package io.fragilebox.gamearea;

/**
 * Allows implementations to check whether given point is placed inside the
 * fence.
 */
public interface InsideCheckable<T> {

  /**
   * Helps to understand whether given point is placed inside the fence or not.
   *
   * @param point Point which will be tested by the method.
   * @return true if point is located <b>inside</b> the fence (points on border
   * not included), false otherwise.
   */
  boolean isInside(Point<T> point);
}
