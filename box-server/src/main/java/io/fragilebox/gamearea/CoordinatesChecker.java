package io.fragilebox.gamearea;

import static io.fragilebox.gamearea.Coordinate.X;
import static io.fragilebox.gamearea.Coordinate.Y;

/**
 * Provides helper methods to check coordinates.
 */
final class CoordinatesChecker {

  private CoordinatesChecker() {
    throw new AssertionError();
  }

  /**
   * Checks whether given point contains required coordinates.
   *
   * @throws IllegalArgumentException if given {@link Point} doesn't contain
   * values for {@link Coordinate#X} and {@link Coordinate#Y}.
   */
  public static void checkCoordinates(Point<Integer> p) {
    Integer xCoord = p.getCoordinate(X);
    Integer yCoord = p.getCoordinate(Y);
    if (xCoord == null || yCoord == null) {
      throw new IllegalArgumentException(
          "Point doesn't contain required coordinates");
    }
  }
}
