package io.fragilebox.gamearea.util;

import static io.fragilebox.gamearea.Coordinate.X;
import static io.fragilebox.gamearea.Coordinate.Y;
import io.fragilebox.gamearea.Point;
import io.fragilebox.gamearea.Point2D;
import io.fragilebox.gamearea.Room;
import java.util.LinkedList;
import java.util.List;

/**
 * Helps to separate room.
 */
public class RoomSeparator {

  /**
   * Separates rooms which have been randomly generated <b>within</b> the given
   * fence.
   *
   * @param rooms Rooms to be separated.
   * @return rooms which have been separated (not overlapped each other).
   */
  public List<Room> separate(final List<Room> rooms) {
    while (isOverlapped(rooms)) {
      for (Room r : rooms) {
        Point<Integer> separation = computeSeparation(r, rooms);

        r.move(new Point2D(
            X, Double.valueOf(r.getTopLeft().getCoordinate(X)
                + separation.getCoordinate(X)).intValue(),
            Y, Double.valueOf(r.getTopLeft().getCoordinate(Y)
                + separation.getCoordinate(Y)).intValue()));
      }
    }
    return rooms;
  }

  private static Point<Integer> computeSeparation(final Room room,
      final List<Room> rooms) {

    List<Room> neighbors = new LinkedList<>();
    for (Room r : rooms) {
      if (r == room) {
        continue;
      }
      if (isOverlapped(room, r)) {
        neighbors.add(r);
      }
    }

    int vX = 0, vY = 0;
    for (Room neighbor : neighbors) {
      vX += neighbor.getTopLeft().getCoordinate(X)
          - room.getTopLeft().getCoordinate(X);
      vY += neighbor.getTopLeft().getCoordinate(Y)
          - room.getTopLeft().getCoordinate(Y);
    }
    if (neighbors.size() != 0) {
      vX /= neighbors.size();
      vY /= neighbors.size();
      vX *= -1;
      vY *= -1;

      double length = Math.sqrt((vX * vX) + (vY * vY));
      double normX = vX / length;
      double normY = vY / length;

      if (normX < 0) {
        vX = -4;
      } else if (normX > 0) {
        vX = 4;
      } else {
        vX = 0;
      }

      if (normY < 0) {
        vY = -4;
      } else if (normY > 0) {
        vY = 4;
      } else {
        vY = 0;
      }
    }
    return new Point2D(X, vX, Y, vY);
  }

  private static boolean isOverlapped(List<Room> rooms) {
    for (Room room : rooms) {
      for (Room r : rooms.subList(0, rooms.size())) {
        if (r == room) {
          continue;
        }
        if (isOverlapped(room, r)) {
          return true;
        }
      }
    }
    return false;
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
}
