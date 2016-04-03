package io.fragilebox.gamearea;

import static io.fragilebox.gamearea.Coordinate.X;
import static io.fragilebox.gamearea.Coordinate.Y;
import static io.fragilebox.gamearea.Coordinate.Z;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class CircleFenceTest {

  @Test
  public void testGetCenter() {
    CenteredFence<Integer> circle = new CircleFence(10, 20, 500);
    assertEquals(circle.getCenter(), new Point2D(X, 10, Y, 20));
  }

  @Test
  public void testGetRandomInnerPoint() {
    CenteredFence<Integer> circle = new CircleFence(550, 550, 500);

    Point<Integer> innerPoint;
    for (int i = 0; i < 100_000; i++) {
      innerPoint = circle.getRandomInnerPoint();

      assertTrue(innerPoint.getCoordinate(X) > 0,
          "coordinate X:" + innerPoint.getCoordinate(X) + " <= 0");
      assertTrue(innerPoint.getCoordinate(Y) > 0,
          "coordinate Y:" + innerPoint.getCoordinate(Y) + " <= 0");
      assertTrue(circle.isInside(innerPoint), "Point is not inside the fence");
    }
  }

  @Test
  public void testIsInside() {
    CenteredFence<Integer> circle = new CircleFence(550, 550, 500);

    assertTrue(circle.isInside(new Point2D(X, 551, Y, 551)));
    assertFalse(circle.isInside(new Point2D(X, 0, Y, 0)));
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testIsInside_NPE() {
    CenteredFence<Integer> circle = new CircleFence(10, 20, 500);
    circle.isInside(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testIsInside_unknownCoordinate() {
    CenteredFence<Integer> circle = new CircleFence(10, 20, 500);
    circle.isInside(new Point2D(X, 9, Z, 19));
  }

  @Test
  public void testGetDelta() {
    CenteredFence<Integer> c = new CircleFence(550, 650, 500);
    Point<Integer> p = new Point2D(X, 600, Y, 480);
    Point<Integer> dX = c.getDelta(X, p);
    Point<Integer> dY = c.getDelta(Y, p);

    assertTrue(dX.getCoordinate(X) > p.getCoordinate(X)
        && dX.getCoordinate(Y).equals(p.getCoordinate(Y)));
    assertTrue(dY.getCoordinate(Y) > p.getCoordinate(Y)
        && dY.getCoordinate(X).equals(p.getCoordinate(X)));
  }

}
