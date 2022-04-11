package com.company;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

class DeserializeJSON {
    private static final Logger logger = Logger.getLogger(CoxModel.class.getName());
    private JSONObject rawJSON;
    private HashMap<String,HashMap<String, Double>> parsedJSON;
    private HashMap<Double, Double> baselineModel;
    private HashMap<Double, Double> cumulativeBaselineModel;

    /**
     * Loads a JSON file and parses it as a JSON file.
     * @param jsonFileName Name of the file in the Resources folder.
     */
    public DeserializeJSON(String jsonFileName) {
        String path = Main.class.getClassLoader().getResource(jsonFileName).getPath();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            logger.log(Level.INFO, path);
            JSONParser jsonParser = new JSONParser();
            this.rawJSON = (JSONObject) jsonParser.parse(bufferedReader);
        } catch (IOException exception) {
            logger.log(Level.SEVERE, "The File was not found.");
            exception.printStackTrace();
        } catch (ParseException exception) {
            logger.log(Level.SEVERE, "Could not parse the file using JSON format.");
            exception.printStackTrace();
        }
        this.getModelParameters();
    }

    /**
     * Convers the inputs to doubles.
     * @param baselineModelInput The Baseline Model as a HashMap.
     * @return The Baseline Model where both inputs and outputs are doubles.
     */
    private HashMap<Double, Double> convertBaselineModelInput(HashMap<String, Double> baselineModelInput){
        HashMap<Double, Double> returnFormattedHashMap = new HashMap<>();
        for (String key : baselineModelInput.keySet()) {
            double keyDouble = Double.parseDouble(key);
            double value = baselineModelInput.get(key);
            returnFormattedHashMap.put(keyDouble, value);
        }
        return returnFormattedHashMap;
    }

    /**
     * Extracts the parameters for the Cox models from the loaded JSON file and converts them to HashMaps.
     */
    private void getModelParameters(){
        this.parsedJSON = new HashMap<String,HashMap<String, Double>>();
        this.parsedJSON.put("meanVector", (HashMap<String, Double>) this.rawJSON.get("mean"));
        this.parsedJSON.put("betaVector", (HashMap<String, Double>) this.rawJSON.get("parameters"));

        this.baselineModel = convertBaselineModelInput( (HashMap<String, Double>) this.rawJSON.get("baseline hazard") );
        this.cumulativeBaselineModel = cumulativeSum(this.baselineModel);
        logger.log(Level.INFO, "Baseline model coefficients: " + this.baselineModel.toString());
        logger.log(Level.INFO, "Mean vector: "+ this.parsedJSON.get("meanVector").toString());
        logger.log(Level.INFO, "Beta coefficients: " + this.parsedJSON.get("betaVector").toString());
    }

    /**
     * Given a functions, creates the cumulative function (where the y-values are the cumulative sum of the y-values).
     * @param unOrderedPairs An (unordered) HashMap of points representing the (x,y) points of a function.
     * @return A HashMap representing the (x,y) points on the cumulative sum function.
     */
    private HashMap<Double, Double> cumulativeSum(HashMap<Double, Double> unOrderedPairs){
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

    public HashMap<Double, Double> getBaselineModel() {
        return baselineModel;
    }

    public HashMap<String, HashMap<String, Double>> getParsedJSON() {
        return parsedJSON;
    }

    public HashMap<Double, Double> getCumulativeBaselineModel() {
        return cumulativeBaselineModel;
    }
}
