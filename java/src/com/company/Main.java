package com.company;

public class Main {

    public static void main(String[] args) {
        CoxModelJSONDeserializer coxModelJSONDeserializer = new CoxModelJSONDeserializer("cph_model_exports.json", "sha256.hash");
        CoxModel coxModel = new CoxModel(coxModelJSONDeserializer);
        System.out.println("The Cox Model has been loaded from JSON");
    }
}
