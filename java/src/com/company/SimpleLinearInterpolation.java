package com.company;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLinearInterpolation {
    private double secondXValue;
    private double secondYValue;
    private double firstXValue;
    private double firstYValue;
    private final static Logger logger = Logger.getLogger(CoxModel.class.getName());

    /**
     * Constructs a line given two points.
     * @param firstPoint A point on the line.
     * @param secondPoint Another point on the line.
     */
    public SimpleLinearInterpolation(double[] firstPoint, double[] secondPoint) {
        logger.log(Level.INFO, Arrays.toString(firstPoint));
        logger.log(Level.INFO, Arrays.toString(secondPoint));
        if(firstPoint.length ==2 && secondPoint.length == 2) {
            this.firstXValue = firstPoint[0];
            this.firstYValue = firstPoint[1];
            this.secondXValue = secondPoint[0];
            this.secondYValue = secondPoint[1];
        }
    }

    /**
     * Given the line, finds the value of the given point on that line.
     * @param interpolationPoint The x-coordinate of the point to interpolate.
     * @return The y-coordinate of the interpolated point.
     */
    public double interpolateValue(double interpolationPoint){
        if(this.secondXValue == this.firstXValue){
            logger.log(Level.SEVERE, "Left X value: " + String.valueOf(this.firstXValue)
                                + "\n Right X value: " + String.valueOf(this.secondXValue));
            throw new ArithmeticException("Not a valid line. The slope is undefined.");
        }
        double slope = (this.secondYValue - this.firstYValue)/(this.secondXValue - this.firstXValue);
        return slope*(interpolationPoint - this.secondXValue) + this.secondYValue;
    }
}
