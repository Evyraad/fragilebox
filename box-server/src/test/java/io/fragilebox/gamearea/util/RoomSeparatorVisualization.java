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
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.apache.commons.math3.distribution.NormalDistribution;

public class RoomSeparatorVisualization extends Application {

  private List<Room> rooms;
  private RoomSeparator separator;
  private int step;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Room Generation");
    Group root = new Group();
    final Canvas canvas = new Canvas(1920, 1080);
    final GraphicsContext gc = canvas.getGraphicsContext2D();
    drawShapes(gc);
    root.getChildren().add(canvas);
    primaryStage.setScene(new Scene(root));
    primaryStage.show();

    primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,
        (KeyEvent event) -> {
      gc.clearRect(0, 0, 1920, 1080);

      if (event.isShiftDown()) {
        rooms = null;
        step = 0;
      }
      drawShapes(gc);
      System.out.println("Step: " + step++);
    });
  }

  private void drawShapes(GraphicsContext gc) {
    CircleFence c = new CircleFence(960, 540, 100);
    if (rooms == null) {
      rooms = generateInnerRooms(c, 150, 4.0);
    }
    if (separator == null) {
      separator = new RoomSeparator();
    }

    separator.separate(rooms).stream().forEach((r) -> {
      drawRoom(gc, r, Color.LIGHTSLATEGRAY, Color.BLUE);
    });
  }

  private static void drawCircle(GraphicsContext gc, CircleFence c,
      Paint color) {
    gc.setFill(color);
    gc.fillOval(c.getCenter().getCoordinate(X) - c.getRadius(),
        c.getCenter().getCoordinate(Y) - c.getRadius(),
        c.getRadius() * 2, c.getRadius() * 2);
  }

  private static void drawPoint(GraphicsContext gc, Point<Integer> p,
      Paint color) {
    final int pRad = 2;
    gc.setFill(color);
    gc.fillOval(p.getCoordinate(X) - pRad, p.getCoordinate(Y) - pRad, pRad,
        pRad);
  }

  private static void drawRoom(GraphicsContext gc, Room r, Paint color,
      Paint borderColor) {
    gc.setFill(color);
    gc.fillRect(r.getTopLeft().getCoordinate(X),
        r.getTopLeft().getCoordinate(Y), r.getWidth(), r.getHeight());
    gc.setFill(borderColor);
    gc.strokeRect(r.getTopLeft().getCoordinate(X),
        r.getTopLeft().getCoordinate(Y), r.getWidth(), r.getHeight());
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
