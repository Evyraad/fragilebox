package io.fragilebox.gamearea;

/**
 * Represents a point with various number of coordinates.
 */
public interface Point<T> {

  /**
   * Returns a values associated with given coordinate.
   *
   * @param coord Type of coordinate.
   * @return Value which is associated with the coordinate, or null.
   */
  T getCoordinate(Coordinate coord);

  /**
   * Computes a normalized vector.
   *
   * @return New {@link Point} with normalized coordinates.
   */
  default Point<T> normalize() {
    throw new UnsupportedOperationException("Default implementation");
  }
}
