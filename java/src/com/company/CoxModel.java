package com.company;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoxModel {
    private static final Logger logger = Logger.getLogger(CoxModel.class.getName());
    private final HashMap<Double, Double> cumulativeBaselineModel;
    private final HashMap<String, Double> betaVector;
    private final HashMap<String, Double> meanVector;

    /**
     * Creates a Cox model from the deserialized object.
     * @param coxModelJSONDeserializer The deserialized JSON object.
     */
    public CoxModel(CoxModelJSONDeserializer coxModelJSONDeserializer){
        HashMap<Double, Double> baselineModel = coxModelJSONDeserializer.getBaselineModel();
        this.meanVector = coxModelJSONDeserializer.getMeanVector();
        this.betaVector = coxModelJSONDeserializer.getBetaVector();
        this.cumulativeBaselineModel = coxModelJSONDeserializer.getCumulativeBaselineModel();
        logger.log(Level.INFO, "Baseline model coefficients: " + baselineModel.toString());
        logger.log(Level.INFO, "Mean vector: "+ this.meanVector.toString());
        logger.log(Level.INFO, "Beta coefficients: " + this.betaVector.toString());
    }

    /**
     * Computes the partial Cox Model at a point, e.g. exp( (x-x_0)*B)
     * @param pointVector The x vector in the model.
     * @return The partial Cox model at the given vector.
     */
    public double evaluatePartialCoxModel(  HashMap<String,Double> pointVector){
        double linearComponentPreExponentiation = 0.0;
        Iterator<String> keyIterator = this.meanVector.keySet().iterator();
        while(keyIterator.hasNext() ){
            String variableName = keyIterator.next();
            double mean = this.meanVector.get(variableName);
            double pointValue = pointVector.get(variableName);
            double beta =  this.betaVector.get(variableName);
            linearComponentPreExponentiation += (pointValue - mean)*beta;
        }
        return Math.exp( linearComponentPreExponentiation);
    }

    /**
     * Compute the cumulative probability at the given array of times, at the specific X vector.
     * @param pointVector The X vector at which to compute the cumulative probability.
     * @param times The array of time to sample the cumulative distribution at.
     * @return An array of the cumulative probability at the specified times.
     */
    public List<Double> cumulativeHazardProbability(HashMap<String, Double> pointVector, double[] times){
        List<Double> cumulativeHazards = new ArrayList<>();
        double partialHazardConstant = evaluatePartialCoxModel(pointVector);
        PiecewiseLinearInterpolator piecewiseLinearInterpolator = new PiecewiseLinearInterpolator(this.cumulativeBaselineModel);
        for (double time : times) {
            double baselineHazardAtSpecificTime = piecewiseLinearInterpolator.linearInterpolation(time);
            logger.log(Level.INFO, String.format("Baseline Hazard at time %f is %f", time, baselineHazardAtSpecificTime) );
            cumulativeHazards.add(partialHazardConstant * baselineHazardAtSpecificTime);
        }
        return cumulativeHazards;
    }
}
