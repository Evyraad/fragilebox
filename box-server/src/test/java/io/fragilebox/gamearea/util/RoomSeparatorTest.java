package io.fragilebox.gamearea.util;

import io.fragilebox.gamearea.AdjustedCenteredFence;
import io.fragilebox.gamearea.CenteredFence;
import io.fragilebox.gamearea.CircleFence;
import static io.fragilebox.gamearea.Coordinate.X;
import static io.fragilebox.gamearea.Coordinate.Y;
import io.fragilebox.gamearea.Point;
import io.fragilebox.gamearea.Point2D;
import io.fragilebox.gamearea.Room;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.distribution.NormalDistribution;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.Test;

public class RoomSeparatorTest {

  private static final int FENCE_RADIUS = 250;
  private static final int ROOMS_NUMBER = 200;

  @Test(timeOut = 10000)
  public void testSeparate() {
    CenteredFence<Integer> circle = new CircleFence(550, 550, FENCE_RADIUS);
    List<Room> innerRooms = generateInnerRooms(circle, ROOMS_NUMBER, 4.0);

    RoomSeparator separator = new RoomSeparator();
    List<Room> separatedRooms = separator.separate(innerRooms);
    for (Room room : separatedRooms) {
      for (Room r : separatedRooms.subList(0, separatedRooms.size())) {
        if (r != room) {
          assertFalse(isOverlapped(r, room));
        }
      }
    }
  }

  private static boolean isOverlapped(Room f, Room s) {
    int fX = f.getTopLeft().getCoordinate(X),
        fY = f.getTopLeft().getCoordinate(Y),
        fWidth = f.getWidth(),
        fHeight = f.getHeight();
    int sX = s.getTopLeft().getCoordinate(X),
        sY = s.getTopLeft().getCoordinate(Y),
        sWidth = s.getWidth(),
        sHeight = s.getHeight();

    return !(fX + fWidth <= sX
        || fX >= sX + sWidth
        || fY + fHeight <= sY
        || fY >= sY + sHeight);
  }

  /**
   * Generates random rooms within the given {@code circle} snapped to grid.
   *
   * @param circle Rooms will be generated within this fence.
   * @param roomNumber Maximum number of rooms to be generated.
   * @param gridStep Size of the grid, in pixels.
   * @return List of generated rooms.
   */
  private static List<Room> generateInnerRooms(CenteredFence<Integer> circle,
      final int roomNumber, final double gridStep) {
    final List<Room> innerRooms = new ArrayList(roomNumber);
    final AdjustedCenteredFence adjustedCircle = new AdjustedCenteredFence(
        circle, gridStep);

    final NormalDistribution distribution = new NormalDistribution();

    for (int i = 0; i < roomNumber; i++) {
      Point<Integer> p = adjustedCircle.getRandomInnerPoint();

      int width = nextWidth(circle, p, distribution, gridStep);
      if (width == -1) {
        continue;
      }
      int height = nextHeight(circle, p, distribution, gridStep, width);
      if (height == -1) {
        continue;
      }
      if ((width / height > 16 / 9) || (height / width > 16 / 9)) {
        continue;
      }

      innerRooms.add(new Room(p.getCoordinate(X), p.getCoordinate(Y),
          width, height));
    }
    return innerRooms;
  }

  private static int nextWidth(CenteredFence<Integer> circle, Point<Integer> p,
      NormalDistribution distribution, double gridStep) {
    final int maxWidth = circle.getDelta(X, p).getCoordinate(X)
        - p.getCoordinate(X);
    if (maxWidth < 0 || maxWidth < (gridStep * 5)) {
      return -1;
    }
    int width;
    do {
      double m = distribution.sample();
      m *= m < 0 ? -1 : 1;
      width = round(m * maxWidth, gridStep);
    } while (width < (gridStep * 4));
    return width;
  }

  private static int nextHeight(CenteredFence<Integer> circle, Point<Integer> p,
      NormalDistribution distribution, double gridStep, int width) {
    Point<Integer> p1;
    do {
      p1 = new Point2D(
          X, round(p.getCoordinate(X) + width, gridStep),
          Y, p.getCoordinate(Y));
      width -= gridStep;
    } while (!circle.isInside(p1));

    int maxHeight = circle.getDelta(Y, p).getCoordinate(Y)
        - p.getCoordinate(Y);
    int maxHeight1 = circle.getDelta(Y, p1).getCoordinate(Y)
        - p1.getCoordinate(Y);
    maxHeight = maxHeight > maxHeight1 ? maxHeight1 : maxHeight;
    if (maxHeight < 0 || maxHeight < (gridStep * 4)) {
      return -1;
    }
    int height;
    do {
      double m = distribution.sample();
      m *= m < 0 ? -1 : 1;
      height = round(m * maxHeight, gridStep);
    } while (height < gridStep * 3);
    return height;
  }

  private static int round(double n, double m) {
    return Double.valueOf(Math.floor(((n + m - 1) / m)) * m).intValue();
  }
}
