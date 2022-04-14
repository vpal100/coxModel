package com.company;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoxModelJSONDeserializer {
    private static final Logger logger = Logger.getLogger(CoxModel.class.getName());
    private JSONObject rawJSON;
    private HashMap<Double, Double> baselineModel;
    private HashMap<Double, Double> cumulativeBaselineModel;
    private HashMap<String, Double> meanVector;
    private HashMap<String, Double> betaVector;

    /**
     * Given the filename of the Python outputted Cox Model as a JSON file and its SHA256 hash, verifies that the hash is correct and parses it as a JSON object.
     * @param jsonFileName Name of the JSON file. Must be in the Resources folder.
     * @param sha256FileName Name of the SHA256 hash of the file. Must be in the Resources folder.
     */
    public CoxModelJSONDeserializer(String jsonFileName, String sha256FileName) {
        try {
            VerifyFileUsingSha256 hashVerifier = new VerifyFileUsingSha256(jsonFileName, sha256FileName);
            String fileAsString = hashVerifier.verifyHashAndReturnFile();
            JSONParser jsonParser = new JSONParser();
            this.rawJSON = (JSONObject) jsonParser.parse(fileAsString);
            this.getModelParameters();
            }
        catch (ParseException exception) {
            logger.log(Level.SEVERE, "Could not parse the file using JSON format.");
            exception.printStackTrace();
            /* Here we will simply let the class variables remain uninitialized - it won't be caught by the upstream consumers and the program will exit
            but the handling of these exceptions needs to be determined by the use cases, and whether a default/backup model should be used */
        }
    }
    /**
     * Converts the inputs to doubles.
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
        this.meanVector = (HashMap<String, Double>) this.rawJSON.get("mean");
        this.betaVector = (HashMap<String, Double>) this.rawJSON.get("parameters");

        this.baselineModel = convertBaselineModelInput( (HashMap<String, Double>) this.rawJSON.get("baseline hazard") );
        this.cumulativeBaselineModel = CDFGenerator.cumulativeSum(this.baselineModel);
        logger.log(Level.INFO, "Baseline model coefficients: " + this.baselineModel.toString());
        logger.log(Level.INFO, "Mean vector: "+ this.meanVector.toString());
        logger.log(Level.INFO, "Beta coefficients: " + this.betaVector.toString());
    }

    public HashMap<Double, Double> getBaselineModel() {
        return this.baselineModel;
    }

    public HashMap<String, Double> getMeanVector() {
        return this.meanVector;
    }

    public HashMap<String, Double> getBetaVector() {
        return this.betaVector;
    }

    public HashMap<Double, Double> getCumulativeBaselineModel() {
        return cumulativeBaselineModel;
    }
}
