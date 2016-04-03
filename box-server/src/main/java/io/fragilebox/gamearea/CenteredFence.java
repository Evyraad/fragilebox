package io.fragilebox.gamearea;

/**
 * An abstract fence which helps to understand relations between other shapes
 * and an area the fence represents, like:
 * <ul>
 * <li>Is shape placed inside the fence?</li>
 * <li>Is shape placed outside the fence?</li>
 * <li>Does shape cross a border of the fence?</li>
 * </ul>
 * <p/>
 * This fence has a center.
 */
public interface CenteredFence<T> extends Fence<T> {

  Point<T> getCenter();
}
