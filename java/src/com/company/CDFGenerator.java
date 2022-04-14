package com.company;

import java.util.HashMap;
import java.util.Iterator;

public class CDFGenerator {

    /**
     * Given a functions, creates the cumulative function (where the y-values are the cumulative sum of the y-values).
     * @param unOrderedPairs An (unordered) HashMap of points representing the (x,y) points of a function.
     * @return A HashMap representing the (x,y) points on the cumulative sum function.
     */
    public static HashMap<Double, Double> cumulativeSum(HashMap<Double, Double> unOrderedPairs){
        Iterator<Double> orderedXValues = unOrderedPairs.keySet().stream().sorted().iterator();
        HashMap<Double, Double> returnCumulativePairs =  new HashMap<>();

        double firstXValue = orderedXValues.next();
        double firstYValue = unOrderedPairs.get(firstXValue);
        returnCumulativePairs.put(firstXValue, firstYValue);

        double runningSum = firstYValue;
        while(orderedXValues.hasNext()){
            double xValue = orderedXValues.next();
            double yValue = unOrderedPairs.get(xValue);
            runningSum += yValue;
            returnCumulativePairs.put(xValue, runningSum);
        }
        return returnCumulativePairs;
    }
}
