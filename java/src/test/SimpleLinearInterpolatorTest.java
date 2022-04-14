package test;

import com.company.SimpleLinearInterpolator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleLinearInterpolatorTest {
    @Test
  public void simpleInterpolationFromTwoPoints() {
      double[] firstPoint = new double[]{1,1};
      double[] secondPoint = new double[]{3,3};
      double interpolatedValue = new SimpleLinearInterpolator(firstPoint,secondPoint).interpolateValue(2);
      assertEquals(2.0, interpolatedValue);
    }
    @Test
    public void errorFromVerticalLine() {
        double[] firstPoint = new double[]{3,1};
        double[] secondPoint = new double[]{3,3};
        assertThrowsExactly(ArithmeticException.class,() -> new SimpleLinearInterpolator(firstPoint,secondPoint).interpolateValue(2) );
    }
}