package test;

import com.company.LinearInterpolationFromSamplePoints;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LinearInterpolationFromSamplePointsTest {
    private final HashMap<Double, Double> dataPoints;

    private LinearInterpolationFromSamplePointsTest(){
        this.dataPoints = new HashMap<>();
        for(int i=0; i<10; i++) {
            this.dataPoints.put( 1.0*i, 3*i +7.);
        }
    }

//    @Test
//    void linearInterpolationUsingPoints() {
//
//        double extremeLeftPointInterpolation = LinearInterpolationFromSamplePoints.linearInterpolationUsingPoints(this.dataPoints, -1.);
//        assertTrue(Math.abs( 3*0.0 + 7.0 - extremeLeftPointInterpolation) <1e-6);
//
//        double extremeRightPointInterpolation = LinearInterpolationFromSamplePoints.linearInterpolationUsingPoints(this.dataPoints, 11.);
//        assertTrue(Math.abs( 3*9 +7. - extremeRightPointInterpolation) <1e-6);
//
//        double pointInDataSet = LinearInterpolationFromSamplePoints.linearInterpolationUsingPoints(this.dataPoints, 2.3);
//        assertTrue(Math.abs( 3*2.3 +7. - pointInDataSet) <1e-6);
//    }

    @Test
    void linearInterpolation() {
        LinearInterpolationFromSamplePoints linearInterpolationClass = new LinearInterpolationFromSamplePoints(this.dataPoints);

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