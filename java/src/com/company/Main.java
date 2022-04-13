package com.company;

public class Main {

    public static void main(String[] args) {
        CoxModel coxModel = new CoxModel("cph_model_exports.json", "sha256.hash");
        System.out.println("The Cox Model has been loaded from JSON");
    }
}
