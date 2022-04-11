package com.company;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LinearInterpolationFromSamplePoints {
    private static final Logger logger = Logger.getLogger(LinearInterpolationFromSamplePoints.class.getName());

    /**
     * Given a list of sample points, and the x-coordinate of a point, uses piece-wise linear interpolation to find the value of the point.
     * @param dataPoints HashMap consisting of (x, y) coordinates, representing the sample data points.
     * @param interpolationPoint The x-coordinate of the point to interpolate.
     * @return The y-coordinate of the interpolated point.
     */
    public static double linearInterpolationUsingPoints(HashMap<Double, Double> dataPoints, double interpolationPoint){
        if(dataPoints.containsKey(interpolationPoint)){
            return dataPoints.get(interpolationPoint);
        }

        Iterator<Double> xValuesIterator = dataPoints.keySet().iterator();
        List<Double> xValuesLessThanPoint = new ArrayList<Double>();
        List<Double> xValuesGreaterThanPoint = new ArrayList<Double>();
        while(xValuesIterator.hasNext()){
            double currentXValue =  xValuesIterator.next();

            if(currentXValue < interpolationPoint ){
                xValuesLessThanPoint.add(currentXValue);
            }
            else if( currentXValue > interpolationPoint){
                xValuesGreaterThanPoint.add(currentXValue);
            }
            else{ //Here the value is one of the provided x's
                return (double) dataPoints.get(currentXValue);
            }
        }
        logger.log(Level.INFO,"Values less than x: %s \n", xValuesLessThanPoint.toString());
        logger.log(Level.INFO,"Values greater than x: %s \n", xValuesGreaterThanPoint.toString());
        Optional<Double> leftXValue = xValuesLessThanPoint.stream().max(Comparator.naturalOrder());
        Optional<Double> rightXValue = xValuesGreaterThanPoint.stream().min(Comparator.naturalOrder());
        if(leftXValue.isEmpty() && rightXValue.isPresent()){
            //Nothing on the left, and elements on the right
            return dataPoints.get(rightXValue.get());
        }
        else if(rightXValue.isEmpty() && leftXValue.isPresent()){
            //Nothing on the right, and elements on the left
            return dataPoints.get(leftXValue.get());
        }
        else if(rightXValue.isEmpty() && leftXValue.isEmpty()){
            logger.log(Level.SEVERE, "The list of elements is empty.");
            return Double.NaN;
        }
        else{
            System.out.printf("Left X: %f  Right X: %f", leftXValue.get(), rightXValue.get());
            double leftYValue = dataPoints.get(leftXValue.get());
            double rightYValue = dataPoints.get(rightXValue.get());
            return new SimpleLinearInterpolation(new double[] {leftXValue.get(), leftYValue},
                    new double[] {rightXValue.get(), rightYValue})
                    .interpolateValue(interpolationPoint);
        }
    }
}
