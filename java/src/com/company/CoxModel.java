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
     * Given the filename of the Python outputted Cox Model at JSON, loads the model into Java.
     * @param jsonFileName The name of the JSON file, must be located in the Resources folder.
     */
    public CoxModel(String jsonFileName){
        DeserializeJSON deserializeJSON = new DeserializeJSON(jsonFileName);
        HashMap<Double, Double> baselineModel = deserializeJSON.getBaselineModel();
        this.meanVector = deserializeJSON.getParsedJSON().get("meanVector");
        this.betaVector = deserializeJSON.getParsedJSON().get("betaVector");
        this.cumulativeBaselineModel = deserializeJSON.getCumulativeBaselineModel();
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
        for (double time : times) {
            double baselineHazardAtSpecificTime = LinearInterpolationFromSamplePoints.linearInterpolationUsingPoints(this.cumulativeBaselineModel, time);
            System.out.printf("Baseline Hazard at time %f is %f \n", time, baselineHazardAtSpecificTime);
            cumulativeHazards.add(partialHazardConstant * baselineHazardAtSpecificTime);
        }
        return cumulativeHazards;
    }
}
