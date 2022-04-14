package com.company;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PiecewiseLinearInterpolator {
    private static final Logger logger = Logger.getLogger(PiecewiseLinearInterpolator.class.getName());
    private final TreeMap<Double, Double> sortedDataPoints;

    /**
     * Initialize the class by making a sorted map of the data points.
     * @param unsortedDataPoints The input map of data points, assumed to be unsorted.
     */
    public PiecewiseLinearInterpolator(HashMap<Double, Double> unsortedDataPoints){
        if(unsortedDataPoints.isEmpty()){
            logger.log(Level.SEVERE, "The HashMap of data points is empty!");
        }
        this.sortedDataPoints = new TreeMap<>();
        this.sortedDataPoints.putAll(unsortedDataPoints);
    }

    /**
     * Given a set of sample data points, interpolate using piecewise linear interpolation.
     * @param interpolationPoint The x-coordinate of the point to interpolate.
     * @return The y-coordinate of the interpolated point.
     */
    public double linearInterpolation(double interpolationPoint){
        if(this.sortedDataPoints.containsKey(interpolationPoint)){
            return this.sortedDataPoints.get(interpolationPoint);
        }
        else {
            Double rightKey = this.sortedDataPoints.higherKey(interpolationPoint);
            Double leftKey = this.sortedDataPoints.lowerKey(interpolationPoint);
            if(rightKey !=null & leftKey != null){
                return new SimpleLinearInterpolator(new double[] {leftKey, this.sortedDataPoints.get(leftKey)},
                        new double[] {rightKey, this.sortedDataPoints.get(rightKey)})
                        .interpolateValue(interpolationPoint);
            }
            else if(rightKey == null & leftKey != null){
                //Nothing on the right of the point, use the rightmost element
                return this.sortedDataPoints.get(leftKey);
            }
            else if(rightKey != null & leftKey == null){
                //Nothing on the left of the point, use the leftmost element
                return this.sortedDataPoints.get(rightKey);
            }
            else{
                logger.log(Level.SEVERE, "The list of data points must be empty.");
                return Double.NaN;
            }
        }
    }
}
