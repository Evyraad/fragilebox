package io.fragilebox.gamearea;

import static io.fragilebox.gamearea.Coordinate.X;
import static io.fragilebox.gamearea.Coordinate.Y;

/**
 * This wrapper generates inner point snapped to a grid, which step is initially
 * provided.
 * <pre>
 *  p = getRandomInnerPoint();
 *  p.x % gridStep == 0;  // true
 *  p.y % gridStep == 0;  // true
 *  isInside(p);          // true
 * </pre>
 */
public final class AdjustedCenteredFence implements CenteredFence<Integer> {

  private final CenteredFence<Integer> circle;
  private final double gridStep;

  public AdjustedCenteredFence(CenteredFence<Integer> circle, double gridStep) {
    this.circle = circle;
    this.gridStep = gridStep;
  }

  @Override
  public Point<Integer> getRandomInnerPoint() {
    Point<Integer> inner = circle.getRandomInnerPoint();
    int delta = 0,
        innerX = inner.getCoordinate(X),
        innerY = inner.getCoordinate(Y);
    int xSign = innerX < circle.getCenter().getCoordinate(X) ? 1 : -1;
    int ySign = innerY < circle.getCenter().getCoordinate(Y) ? 1 : -1;

    Point<Integer> adjusted;
    do {
      adjusted = new Point2D(
          X, round(innerX + (delta++ * xSign), gridStep),
          Y, round(innerY + (delta++ * ySign), gridStep));
    } while (!circle.isInside(adjusted));
    return adjusted;
  }

  @Override
  public Point<Integer> getDelta(Coordinate coordinate, Point<Integer> origin) {
    return circle.getDelta(coordinate, origin);
  }

  @Override
  public boolean isInside(Point<Integer> point) {
    return circle.isInside(point);
  }

  private static int round(double n, double m) {
    return Double.valueOf(Math.floor(((n + m - 1) / m)) * m).intValue();
  }

  @Override
  public Point<Integer> getCenter() {
    return circle.getCenter();
  }

}
