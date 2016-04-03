package io.fragilebox.gamearea;

import static io.fragilebox.gamearea.Coordinate.X;
import static io.fragilebox.gamearea.Coordinate.Y;
import static io.fragilebox.gamearea.Coordinate.Z;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class RoomTest {

  @Test
  public void testIsInside() {
    Room r = new Room(10, 20, 100, 50);

    assertFalse(r.isInside(new Point2D(X, 10, Y, 20)));
    assertFalse(r.isInside(new Point2D(X, 15, Y, 20)));
    assertFalse(r.isInside(new Point2D(X, 10, Y, 21)));
    assertTrue(r.isInside(new Point2D(X, 11, Y, 21)));
    assertFalse(r.isInside(new Point2D(X, 110, Y, 21)));
    assertFalse(r.isInside(new Point2D(X, 50, Y, 70)));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testIsInside_unknownCoordinate() {
    new Room(10, 20, 100, 50).isInside(new Point2D(X, 10, Z, 20));
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testIsInside_NPE() {
    new Room(10, 20, 100, 50).isInside(null);
  }

  @Test
  public void testGetRandomInnerPoint() {
    Room r = new Room(75, 57, 200, 200);
    Point<Integer> p;

    for (int i = 0; i < 100_000; i++) {
      p = r.getRandomInnerPoint();
      assertTrue(r.isInside(p), p.toString());
    }
  }

  @Test
  public void testMove() {
    Room r = new Room(10, 20, 100, 50);
    r.move(new Point2D(X, 5, Y, 10));
    assertEquals(r.getTopLeft(), new Point2D(X, 5, Y, 10));

    r = new Room(5, 7, 100, 50);
    r.move(75, 57);
    assertEquals(r.getTopLeft(), new Point2D(X, 75, Y, 57));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testMove_unknownCoordinate() {
    new Room(10, 20, 100, 50).move(new Point2D(Z, 5, Y, 10));
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testMove_NPE() {
    new Room(10, 20, 100, 50).move(null);
  }

  @Test
  public void testGetCenter() {
    Room r = new Room(10, 20, 100, 50);
    assertEquals(r.getCenter(), new Point2D(X, 60, Y, 45));

    r = new Room(10, 20, 107, 53);
    assertEquals(r.getCenter(), new Point2D(X, 63, Y, 46));
  }

  @Test
  public void testGetDelta() {
    Room r = new Room(10, 20, 100, 50);

    assertEquals(r.getDelta(X, new Point2D(X, 15, Y, 25)),
        new Point2D(X, 110, Y, 25));
    assertEquals(r.getDelta(Y, new Point2D(X, 15, Y, 25)),
        new Point2D(X, 15, Y, 70));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetDelta_unknownCoordinate() {
    Room r = new Room(10, 20, 100, 50);
    r.getDelta(Z, new Point2D(X, 15, Y, 25));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testGetDelta_outsidePoint() {
    Room r = new Room(10, 20, 100, 50);
    r.getDelta(X, new Point2D(X, 111, Y, 25));
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testGetDelta_NPE() {
    Room r = new Room(10, 20, 100, 50);
    r.getDelta(null, new Point2D(X, 111, Y, 25));
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void testGetDelta_NPE1() {
    Room r = new Room(10, 20, 100, 50);
    r.getDelta(X, null);
  }
}
