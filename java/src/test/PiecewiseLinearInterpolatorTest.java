package test;

import com.company.PiecewiseLinearInterpolator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PiecewiseLinearInterpolatorTest {
    private final HashMap<Double, Double> dataPoints;

    private PiecewiseLinearInterpolatorTest(){
        this.dataPoints = new HashMap<>();
        for(int i=0; i<10; i++) {
            this.dataPoints.put( 1.0*i, 3*i +7.);
        }
    }

    @Test
    void linearInterpolation() {
        PiecewiseLinearInterpolator linearInterpolationClass = new PiecewiseLinearInterpolator(this.dataPoints);

        double pointInDataSet = linearInterpolationClass.linearInterpolation(2.6);
        assertTrue(Math.abs( 3*2.6 +7. - pointInDataSet) <1e-6);

        double extremeLeftPointInterpolation = linearInterpolationClass.linearInterpolation( -1.);
        assertTrue(Math.abs( 3*0.0 + 7.0 - extremeLeftPointInterpolation) <1e-6);

        double extremeRightPointInterpolation = linearInterpolationClass.linearInterpolation(11.);
        assertTrue(Math.abs( 3*9 +7. - extremeRightPointInterpolation) <1e-6);

        double pointInSample = linearInterpolationClass.linearInterpolation(5.);
        assertTrue(Math.abs( 3*5 +7. - pointInSample) <1e-6);
    }
}