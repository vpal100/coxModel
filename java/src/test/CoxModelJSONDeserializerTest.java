package test;

import com.company.CoxModelJSONDeserializer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CoxModelJSONDeserializerTest {
    private final CoxModelJSONDeserializer coxModelJSONDeserializer = new CoxModelJSONDeserializer("cph_model_exports.json", "sha256.hash");

    @Test
    void getBaselineModel() {
        HashMap<Double,Double> baselineModel = this.coxModelJSONDeserializer.getBaselineModel();
        double expectedValueAt10 = 0.002032059184125721;
        assertTrue(Math.abs(baselineModel.get(10.) - expectedValueAt10) < 1e-6);

        double expectedValueAt27 = 0.004731770313380664;
        assertTrue(Math.abs(baselineModel.get(27.) - expectedValueAt27) < 1e-6);

        double expectedValueAt38 = 0.002595540829427537;
        assertTrue(Math.abs(baselineModel.get(38.) - expectedValueAt38) < 1e-6);

    }

    @Test
    void getMeanVector() {
        HashMap<String, Double> meanVector = this.coxModelJSONDeserializer.getMeanVector();

        double expectedValueAtRace = 0.8773148148148148;
        assertTrue(Math.abs(meanVector.get("race") - expectedValueAtRace) < 1e-6);
    }

    @Test
    void getBetaVector() {
        HashMap<String, Double> betaVector = this.coxModelJSONDeserializer.getBetaVector();

        double expectedValueAtParo = -0.0848710735049427;
        assertTrue(Math.abs(betaVector.get("paro") - expectedValueAtParo) < 1e-6);
    }

    @Test
    void getCumulativeBaselineModel() {
        HashMap<Double, Double> cumulativeBaselineModel = this.coxModelJSONDeserializer.getCumulativeBaselineModel();

        double expectedValueAt7 = 0.01381023231607111;
        assertTrue(Math.abs(cumulativeBaselineModel.get(7.) - expectedValueAt7) < 1e-6);

        double expectedValueAt39 = 0.18303689843676504;
        assertTrue(Math.abs(cumulativeBaselineModel.get(39.) - expectedValueAt39) < 1e-6);
    }

    @Test
    void throwsErrorThatIsNotCaught(){
        assertThrowsExactly(NullPointerException.class, () -> {new CoxModelJSONDeserializer("error", "error.hash");}) ;
    }
}