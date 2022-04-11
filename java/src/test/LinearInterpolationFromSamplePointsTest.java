package test;

import com.company.LinearInterpolationFromSamplePoints;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LinearInterpolationFromSamplePointsTest {

    @Test
    void linearInterpolationUsingPoints() {
        HashMap<Double, Double> dataPoints = new HashMap<>();
        for(int i=0; i<10; i++) {
            dataPoints.put( 1.0*i, 3*i +7.);
        }
        double extremeLeftPointInterpolation = LinearInterpolationFromSamplePoints.linearInterpolationUsingPoints(dataPoints, -1.);
        assertTrue(Math.abs( 3*0.0 + 7.0 - extremeLeftPointInterpolation) <1e-6);

        double extremeRightPointInterpolation = LinearInterpolationFromSamplePoints.linearInterpolationUsingPoints(dataPoints, 11.);
        assertTrue(Math.abs( 3*9 +7. - extremeRightPointInterpolation) <1e-6);

        double pointInDataSet = LinearInterpolationFromSamplePoints.linearInterpolationUsingPoints(dataPoints, 2.3);
        assertTrue(Math.abs( 3*2.3 +7. - pointInDataSet) <1e-6);
    }
}