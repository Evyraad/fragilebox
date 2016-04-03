package io.fragilebox.gamearea;

import static io.fragilebox.gamearea.Coordinate.X;
import static io.fragilebox.gamearea.Coordinate.Y;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class Point2DTest {

  private static final Logger LOG = LoggerFactory.getLogger(Point2DTest.class);

  @Test
  public void testGetCoordinate() {
    Point p = new Point2D(X, 75, Y, 57);

    assertEquals(p.getCoordinate(X), 75);
    assertEquals(p.getCoordinate(Y), 57);

    p = new Point2D(Y, 75, X, 57);

    assertEquals(p.getCoordinate(X), 57);
    assertEquals(p.getCoordinate(Y), 75);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testGetCoordinate_X_NPE() {
    new Point2D(null, 75, Y, 57);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testGetCoordinate_Y_NPE() {
    new Point2D(X, 75, null, 57);
  }

  @Test
  public void testEquals() {
    Point p = new Point2D(X, 75, Y, 57);
    Point likePoint = (coord) -> {
      switch (coord) {
        case X:
          return 75;
        case Y:
          return 57;
        default:
          return null;
      }
    };

    // Reflexive
    assertTrue(p.equals(p));
    assertTrue(p.equals(likePoint));
    assertFalse(p.equals(null));

    // Symmetric
    Point s0 = new Point2D(X, 75, Y, 57);
    Point s1 = new Point2D(X, 75, Y, 57);

    assertTrue(s0.equals(s1));
    assertTrue(s1.equals(s0));

    // Transitive
    Point t0 = new Point2D(X, 75, Y, 57);
    Point t1 = new Point2D(X, 75, Y, 57);
    Point t2 = new Point2D(X, 75, Y, 57);

    assertTrue(t0.equals(t1));
    assertTrue(t1.equals(t2));
    assertTrue(t0.equals(t2));

    // Consistent
    Point c0 = new Point2D(X, 75, Y, 57);
    Point c1 = new Point2D(X, 75, Y, 57);

    for (int i = 0; i < 1000; i++) {
      assertTrue(c0.equals(c1));
    }
  }

  @Test
  public void testToString() {
    LOG.info(new Point2D(X, 75, Y, 57).toString());
  }
}
