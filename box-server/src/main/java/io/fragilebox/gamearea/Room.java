package io.fragilebox.gamearea;

import static io.fragilebox.gamearea.Coordinate.X;
import static io.fragilebox.gamearea.Coordinate.Y;
import static io.fragilebox.gamearea.CoordinatesChecker.checkCoordinates;
import java.util.Random;

/**
 * Rectangle area which can be described by coordinates of top-left corner,
 * width and height.
 */
public final class Room implements CenteredFence<Integer> {

  private final int width;
  private final int height;

  private Point<Integer> topLeft;
  private Point<Integer> center;

  private Random random;

  /**
   * Constructs new room.
   *
   * @param xCoord X coordinate of the top-left corner.
   * @param yCoord Y coordinate of the top-left corner.
   * @param width Width of the room.
   * @param height Height of the room.
   */
  public Room(int xCoord, int yCoord, int width, int height) {
    this.width = width;
    this.height = height;
    this.topLeft = new Point2D(X, xCoord, Y, yCoord);
    this.center = calcCenter(topLeft, this.width, this.height);
  }

  /**
   * {@inheritDoc }
   *
   * @throws NullPointerException if argument is null.
   * @throws IllegalArgumentException if given {@link Point} doesn't contain
   * values for {@link Coordinate#X} and {@link Coordinate#Y}.
   */
  @Override
  public boolean isInside(Point<Integer> point) {
    if (point == null) {
      throw new NullPointerException("Point is null");
    }
    checkCoordinates(point);

    int topLeftX = topLeft.getCoordinate(X);
    int topLeftY = topLeft.getCoordinate(Y);
    int xc = point.getCoordinate(X);
    int yc = point.getCoordinate(Y);
    return xc > topLeftX
        && yc > topLeftY
        && xc < (topLeftX + width)
        && yc < (topLeftY + height);
  }

  /**
   * {@inheritDoc }
   */
  @Override
  public Point<Integer> getRandomInnerPoint() {
    if (random == null) {
      random = new Random(System.currentTimeMillis());
    }
    int x = random.nextInt(width);
    x = x == 0 ? topLeft.getCoordinate(X) + 1 : topLeft.getCoordinate(X) + x;
    int y = random.nextInt(height);
    y = y == 0 ? topLeft.getCoordinate(Y) + 1 : topLeft.getCoordinate(Y) + y;
    return new Point2D(X, x, Y, y);
  }

  /**
   * {@inheritDoc }
   *
   * @throws NullPointerException if one of arguments is null.
   * @throws IllegalArgumentException if {@code coord} is not suitable, or
   * {@code origin} point is placed outside of the room.
   */
  @Override
  public Point<Integer> getDelta(final Coordinate coord, Point<Integer> origin) {
    if (coord == null || origin == null) {
      throw new NullPointerException("Argument is null");
    }
    if (!this.isInside(origin)) {
      throw new IllegalArgumentException("Origin point is placed outside");
    }

    int originX = origin.getCoordinate(X);
    int originY = origin.getCoordinate(Y);
    int topLeftX = topLeft.getCoordinate(X);
    int topLeftY = topLeft.getCoordinate(Y);
    Point<Integer> result;
    switch (coord) {
      case X:
        result = new Point2D(X, width + topLeftX, Y, originY);
        break;
      case Y:
        result = new Point2D(X, originX, Y, height + topLeftY);
        break;
      default:
        throw new IllegalArgumentException("Coordinate is not suitable: "
            + coord);
    }
    return result;
  }

  /**
   * Returns a center of the room.
   * <p/>
   * Sometimes, it is not possible to divide width or height on two equal parts
   * (because they are represented as {@link Integer}), thus result will
   * represent a point near center. Its coordinates will be smaller than
   * coordinates of "real" center.
   */
  @Override
  public Point<Integer> getCenter() {
    return center;
  }

  private static Point<Integer> calcCenter(final Point<Integer> topLeft,
      int width, int height) {
    assert topLeft != null : "Top-left point is null";
//    assert topLeft.getCoordinate(X) >= 0 :
//        "X coordinate of top-left is negative";
//    assert topLeft.getCoordinate(Y) >= 0 :
//        "Y coordinate of top-left is negative";

    return new Point2D(X, width / 2 + topLeft.getCoordinate(X),
        Y, height / 2 + topLeft.getCoordinate(Y));
  }

  /**
   * Moves the room to the new place using top-left corner.
   *
   * @param newTopLeft New place of the room.
   * @throws NullPointerException if argument is null.
   * @throws IllegalArgumentException if given {@link Point} doesn't contain
   * values for {@link Coordinate#X} and {@link Coordinate#Y}.
   */
  public void move(Point<Integer> newTopLeft) {
    if (newTopLeft == null) {
      throw new NullPointerException("New top-left is null");
    }
    checkCoordinates(newTopLeft);
    this.topLeft = newTopLeft;
    this.center = calcCenter(topLeft, this.width, this.height);
  }

  /**
   * Moves the room to the new place using top-left corner.
   *
   * @param xCoord New X coordinate of the top-left corner.
   * @param yCoord New Y coordinate of the top-left corner.
   */
  public void move(int xCoord, int yCoord) {
    this.move(new Point2D(X, xCoord, Y, yCoord));
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  /**
   * Returns the top-left corner of the room.
   *
   * @return top-left corner.
   */
  public Point<Integer> getTopLeft() {
    return topLeft;
  }
}
