package io.fragilebox.gamearea;

/**
 * An abstract fence which helps to understand relations between other shapes
 * and an area the fence represents, like:
 * <ul>
 * <li>Is shape placed inside the fence?</li>
 * <li>Is shape placed outside the fence?</li>
 * <li>Does shape cross a border of the fence?</li>
 * </ul>
 */
public interface Fence<T> extends InsideCheckable<T> {

  /**
   * Generates a point with random coordinates inside the fence. These
   * coordinates are not coordinates from the border of the fence.
   *
   * @return Instance of {@link Point} which represents a point inside the
   * fence.
   */
  Point<T> getRandomInnerPoint();

  /**
   * Returns a point which given coordinate is modified the way that it is
   * placed on the border of the fence (according to the given point).
   *
   * @param coordinate This coordinate of the given point will be modified in
   * order to obtain required point.
   * @param origin This point is a source of original coordinates to find
   * required point.
   * @return Point which coordinates are the same as given one except
   * {@code coordinate}. This point <b>is placed on the border</b> of the fence.
   */
  Point<T> getDelta(Coordinate coordinate, Point<T> origin);
}
