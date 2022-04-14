package test;

import com.company.CoxModel;
import com.company.CoxModelJSONDeserializer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoxModelTest {
    private final double[] cumulativeExpectedValueTest1;
    private final double[] cumulativeExpectedValueTest2;
    private final double[] timeValues;
    private final HashMap<String, Double> testCase;
    private final HashMap<String, Double> testCase2;
    private final CoxModelJSONDeserializer coxModelJSONDeserializer;

    private CoxModelTest() {
        String jsonFileNameForTest = "cph_model_exports.json";
        String sha256FileNameForTests = "sha256.hash";
        this.coxModelJSONDeserializer = new CoxModelJSONDeserializer(jsonFileNameForTest, sha256FileNameForTests);

        this.testCase = new HashMap<>();
        this.testCase.put("week", 20.0);
        this.testCase.put("arrest", 1.);
        this.testCase.put("fin", 0.);
        this.testCase.put("age", 27.);
        this.testCase.put("race", 1.);
        this.testCase.put("wexp", 0.);
        this.testCase.put("mar", 0.);
        this.testCase.put("paro", 1.);
        this.testCase.put("prio", 3.);

        this.testCase2 = new HashMap<>();
        this.testCase2.put("week", 18.0);
        this.testCase2.put("arrest", 1.);
        this.testCase2.put("fin", 0.);
        this.testCase2.put("age", 22.);
        this.testCase2.put("race", 1.);
        this.testCase2.put("wexp", 0.);
        this.testCase2.put("mar", 0.);
        this.testCase2.put("paro", 0.);
        this.testCase2.put("prio", 4.);

        this.timeValues = new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 30.0, 31.0, 32.0, 33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0, 49.0, 50.0, 52.0};
        
        this.cumulativeExpectedValueTest1 = new double[]{0.00238714910313134, 0.004781366365387238, 0.007177281010030146, 0.009578037208232281, 0.011986359939823856, 0.014404720827129163, 0.016836418737338762, 0.029015105048284907, 0.03395578786325998, 0.03643312485079074, 0.04143565606776384, 0.046537572022636274, 0.049094478332202963, 0.05679759339145242, 0.06199993227209216, 0.0672322710741953, 0.07510771674658283, 0.08312221204540206, 0.08852565146692094, 0.10213222129025544, 0.1076344196427586, 0.11039726758270936, 0.11317589608052012, 0.12430511843681835, 0.1327189483915821, 0.14131863714833037, 0.1470872632323592, 0.15288794210999487, 0.15874660489680484, 0.1616960362826655, 0.16762351348991267, 0.17359357241098358, 0.17964824485177738, 0.19186188151972794, 0.2011375231359383, 0.21362633774636594, 0.21679063008077132, 0.22314511413967547, 0.2359141749082845, 0.24241133199199558, 0.25545076865542693, 0.2620662228725615, 0.2687311954305072, 0.2822091343556566, 0.2856150886090638, 0.29245805757096077, 0.30973560287930607, 0.32024857189806555, 0.33449517794405004};
        this.cumulativeExpectedValueTest2 = new double[]{0.0037948944086487043, 0.007601025198596775, 0.011409854348241504, 0.015226380203763124, 0.01905493471523035, 0.02289944706567011, 0.02676516152434061, 0.04612584097476588, 0.053980134435053374, 0.05791840216620894, 0.06587101715765604, 0.07398162587712981, 0.07804638640879709, 0.09029216872259462, 0.09856242159625825, 0.10688036589148914, 0.11940010531981245, 0.13214089447191638, 0.14073084053824964, 0.16236145241570943, 0.17110839735341807, 0.17550054704591628, 0.17991778337867076, 0.1976101108655142, 0.21098572959363235, 0.22465681144448713, 0.23382730139970195, 0.2430487462647214, 0.2523623692062709, 0.25705113400115254, 0.26647415248025313, 0.27596486388540026, 0.2855900869439968, 0.3050063276135358, 0.31975198403684524, 0.3396056800936158, 0.3446360132518644, 0.35473785230052307, 0.3750370607793425, 0.3853657097341752, 0.40609473969760523, 0.41661144776009873, 0.427206876030904, 0.4486330010266429, 0.4540475085391455, 0.4649259009353286, 0.49239232940425315, 0.5091049877360072, 0.5317530768541978};
    }
    @Test
    void evaluatePartialCoxModel() {
    CoxModel coxModel = new CoxModel(this.coxModelJSONDeserializer);
    double modelOutput = coxModel.evaluatePartialCoxModel(this.testCase);
    double expectedReturn = 1.2191263949807742;
    assertTrue(Math.abs(modelOutput - expectedReturn) <1e-6 );
    }

    @Test
    void cumulativeHazardModel1() {
        CoxModel coxModel = new CoxModel(this.coxModelJSONDeserializer);
        List<Double> modelOutput = coxModel.cumulativeHazardProbability(this.testCase, this.timeValues);
        assertEquals(this.cumulativeExpectedValueTest1.length, modelOutput.size());
        for (int index = 0; index < modelOutput.size(); index++) {
            assertTrue(Math.abs(modelOutput.get(index) - this.cumulativeExpectedValueTest1[index]) < 1e-6);
        }
    }

    @Test
    void cumulativeHazardModel2() {
        CoxModel coxModel = new CoxModel(this.coxModelJSONDeserializer);
        List<Double> modelOutput = coxModel.cumulativeHazardProbability(this.testCase2, this.timeValues);
        assertEquals(this.cumulativeExpectedValueTest2.length, modelOutput.size());
        for (int index = 0; index < modelOutput.size(); index++) {
            assertTrue(Math.abs(modelOutput.get(index) - this.cumulativeExpectedValueTest2[index]) < 1e-6);
        }
    }
}