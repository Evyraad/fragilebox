package io.fragilebox.gamearea;

/**
 * Immutable point which has only two coordinates, x and y.
 */
public final class Point2D implements Point<Integer> {

  private final Coordinate firstCoord;
  private final Integer firstVal;
  private final Coordinate secondCoord;
  private final Integer secondVal;

  private volatile String stringVal;
  private volatile int hashCode;

  /**
   * Constructs new {@link Point2D}.
   *
   * @throws NullPointerException if {@code xType} or {@code yType} is null.
   */
  public Point2D(Coordinate xType, int xVal, Coordinate yType, int yVal) {
    if (xType == null || yType == null) {
      throw new NullPointerException("Coordinate is null");
    }
    this.firstCoord = xType;
    this.firstVal = xVal;
    this.secondCoord = yType;
    this.secondVal = yVal;
  }

  /**
   * {@inheritDoc }
   */
  @Override
  public Integer getCoordinate(Coordinate coord) {
    if (firstCoord.equals(coord)) {
      return firstVal;
    } else if (secondCoord.equals(coord)) {
      return secondVal;
    } else {
      return null;
    }
  }  

  @Override
  public String toString() {
    if (stringVal == null) {
      StringBuilder sb = new StringBuilder()
          .append(this.getClass().getSimpleName())
          .append(" {").append(firstCoord).append(':').append(firstVal)
          .append(", ").append(secondCoord).append(':').append(secondVal)
          .append('}');
      this.stringVal = sb.toString();
    }
    return this.stringVal;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Point)) {
      return false;
    }
    Point<?> point = (Point) o;
    return firstVal.equals(point.getCoordinate(firstCoord))
        && secondVal.equals(point.getCoordinate(secondCoord));
  }

  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = 75;
      result = 31 * result + firstCoord.hashCode();
      result = 31 * result + firstVal;
      result = 31 * result + secondCoord.hashCode();
      result = 31 * result + secondVal;
    }
    return result;
  }

}
