package test;

import com.company.CumulativeSumOfFunction;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CumulativeSumOfFunctionTest {

    @Test
    void cumulativeSum() {
        HashMap<Double, Double> dataPoints = new HashMap<>();
        dataPoints.put(1.0, 1.0);
        dataPoints.put(3.0, 1.0);
        dataPoints.put(2.0, 1.0);

        HashMap<Double, Double> cumulativeSumFunction = CumulativeSumOfFunction.cumulativeSum(dataPoints);
        assertTrue(Math.abs(cumulativeSumFunction.get(3.) - 3) <1e-6);
    }
}