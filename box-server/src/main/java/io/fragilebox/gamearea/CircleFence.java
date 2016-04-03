package io.fragilebox.gamearea;

import static io.fragilebox.gamearea.Coordinate.X;
import static io.fragilebox.gamearea.Coordinate.Y;
import static io.fragilebox.gamearea.CoordinatesChecker.checkCoordinates;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * The circle fence which has a center and radius.
 * </p>
 * Note that the class is immutable.
 */
public final class CircleFence implements CenteredFence<Integer> {

  private final int centerX;
  private final int centerY;
  private final int radius;
  private final Point<Integer> centerPoint;

  private NormalDistribution distribution = new NormalDistribution();

  public CircleFence(int centerX, int centerY, int radius) {
    this.centerX = centerX;
    this.centerY = centerY;
    this.radius = radius;
    this.centerPoint = new Point2D(X, centerX, Y, centerY);
  }

  /**
   * {@inheritDoc }
   *
   * @return Center of the fence.
   */
  @Override
  public Point<Integer> getCenter() {
    return centerPoint;
  }

  public int getRadius() {
    return radius;
  }

  /**
   * {@inheritDoc }
   *
   * @return Instance of {@link Point} which result from
   * {@link CircleFence#isInside()} method is {@code true}.
   */
  @Override
  public Point<Integer> getRandomInnerPoint() {
    // See http://stackoverflow.com/a/5838055 for explanation.

    double angle = 2 * Math.PI * distribution.sample();
    double u = nextDouble() + nextDouble();

    double r;
    if (u > 1) {
      r = (2 - u) * radius;
    } else if (u < 1) {
      r = u * radius;
    } else {
      r = nextDouble() * radius;
    }
    return new Point2D(
        X, Double.valueOf(r * Math.cos(angle)).intValue() + centerX,
        Y, Double.valueOf(r * Math.sin(angle)).intValue() + centerY);
  }

  /**
   * Returns double from 0.0 (inclusively) to 1.0 (exclusively).
   */
  private double nextDouble() {
    double v;
    do {
      v = distribution.sample();
      v *= v < 0 ? -1 : 1;
    } while (v >= 1.0);
    return v;
  }

  /**
   * {@inheritDoc }
   *
   * @param point Point which will be tested.
   * @return true if point is <b>inside</b> the fence (borders don't contain
   * this point), false otherwise.
   *
   * @throws NullPointerException if arguments are null.
   * @throws IllegalArgumentException if {@code point} doesn't have values
   * corresponding to {@link Coordinate#X} and {@link Coordinate#Y}.
   */
  @Override
  public boolean isInside(Point<Integer> point) {
    if (point == null) {
      throw new NullPointerException("Point is null");
    }
    checkCoordinates(point);

    int x = point.getCoordinate(X);
    int y = point.getCoordinate(Y);
    return (Math.pow((x - centerX), 2) + Math.pow((y - centerY), 2)) < Math
        .pow(radius, 2);
  }

  /**
   * {@inheritDoc }
   * <p/>
   * <b>Note</b> that, according to the {@link Integer} nature of the
   * coordinates, method {@link CircleFence#isInside()} <b>may</b> return true
   * for resulted point. Generally speaking, result of
   * {@link CircleFence#isInside()} for resulted point is undefined.
   *
   * @throws NullPointerException if one of arguments is null.
   * @throws IllegalArgumentException if {@code coord} is not suitable, or
   * {@code origin} point is placed outside of the room.
   */
  @Override
  public Point<Integer> getDelta(Coordinate coord, Point<Integer> origin) {
    if (coord == null || origin == null) {
      throw new NullPointerException("Argument is null");
    }
    if (!this.isInside(origin)) {
      throw new IllegalArgumentException("Origin point is placed outside");
    }

    Point<Integer> result;
    switch (coord) {
      case X:
        double res = Math.sqrt(Math.pow(this.getRadius(), 2)
            - Math.pow(origin.getCoordinate(Y) - centerY, 2));
        int v = origin.getCoordinate(X) - centerX;

        if (origin.getCoordinate(X) < centerX) {
          res += v < 0 ? v * -1 : v;
        } else {
          res -= v < 0 ? v * -1 : v;
        }
        result = new Point2D(
            X, origin.getCoordinate(X) + Double.valueOf(res).intValue(),
            Y, origin.getCoordinate(Y));
        break;
      case Y:
        res = Math.sqrt(Math.pow(this.getRadius(), 2)
            - Math.pow(origin.getCoordinate(X) - centerX, 2));
        v = origin.getCoordinate(Y) - centerY;

        if (origin.getCoordinate(Y) < centerY) {
          res += v < 0 ? v * -1 : v;
        } else {
          res -= v < 0 ? v * -1 : v;
        }
        result = new Point2D(
            X, origin.getCoordinate(X),
            Y, origin.getCoordinate(Y) + Double.valueOf(res).intValue());
        break;
      default:
        throw new IllegalArgumentException("Coordinate is not suitable: "
            + coord);
    }
    return result;
  }

}
