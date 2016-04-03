package io.fragilebox.gamearea;

import static io.fragilebox.gamearea.Coordinate.X;
import static io.fragilebox.gamearea.Coordinate.Y;
import static io.fragilebox.gamearea.Coordinate.Z;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class AdjustedCenteredFenceTest {

  private static final double GRID_STEP = 4.0;

  @Test
  public void testGetRandomInnerPoint() {
    Fence<Integer> c = new AdjustedCenteredFence(
        new CircleFence(500, 500, 200), GRID_STEP);
    for (int i = 0; i < 1000; i++) {
      Point<Integer> inner = c.getRandomInnerPoint();

      assertTrue(c.isInside(inner));
      assertEquals(inner.getCoordinate(X) % GRID_STEP, 0.0);
      assertEquals(inner.getCoordinate(Y) % GRID_STEP, 0.0);
    }
  }

  @Test
  public void testGetCenter() {
    CenteredFence<Integer> circle = new AdjustedCenteredFence(
        new CircleFence(10, 20, 500), GRID_STEP);
    assertEquals(circle.getCenter(), new Point2D(X, 10, Y, 20));
  }

  @Test
  public void testIsInside() {
    CenteredFence<Integer> circle = new AdjustedCenteredFence(
        new CircleFence(550, 550, 500), GRID_STEP);

    assertTrue(circle.isInside(new Point2D(X, 551, Y, 551)));
    assertFalse(circle.isInside(new Point2D(X, 0, Y, 0)));
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testIsInside_NPE() {
    CenteredFence<Integer> circle = new AdjustedCenteredFence(
        new CircleFence(10, 20, 500), GRID_STEP);
    circle.isInside(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testIsInside_unknownCoordinate() {
    CenteredFence<Integer> circle = new AdjustedCenteredFence(
        new CircleFence(10, 20, 500), GRID_STEP);
    circle.isInside(new Point2D(X, 9, Z, 19));
  }

  @Test
  public void testGetDelta() {
    CenteredFence<Integer> c = new AdjustedCenteredFence(
        new CircleFence(550, 650, 500), GRID_STEP);
    Point<Integer> p = new Point2D(X, 600, Y, 480);
    Point<Integer> dX = c.getDelta(X, p);
    Point<Integer> dY = c.getDelta(Y, p);

    assertTrue(dX.getCoordinate(X) > p.getCoordinate(X)
        && dX.getCoordinate(Y).equals(p.getCoordinate(Y)));
    assertTrue(dY.getCoordinate(Y) > p.getCoordinate(Y)
        && dY.getCoordinate(X).equals(p.getCoordinate(X)));
  }

}
