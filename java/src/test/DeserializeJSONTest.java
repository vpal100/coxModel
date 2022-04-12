package test;

import com.company.DeserializeJSON;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DeserializeJSONTest {
    private final DeserializeJSON deserializeJSON= new DeserializeJSON("cph_model_exports.json");

    @Test
    void getBaselineModel() {
        HashMap<Double,Double> baselineModel = this.deserializeJSON.getBaselineModel();
        double expectedValueAt10 = 0.002032059184125721;
        assertTrue(Math.abs(baselineModel.get(10.) - expectedValueAt10) < 1e-6);

        double expectedValueAt27 = 0.004731770313380664;
        assertTrue(Math.abs(baselineModel.get(27.) - expectedValueAt27) < 1e-6);

        double expectedValueAt38 = 0.002595540829427537;
        assertTrue(Math.abs(baselineModel.get(38.) - expectedValueAt38) < 1e-6);

    }

    @Test
    void getParsedJSON() {
        HashMap<String, Double> meanVector = this.deserializeJSON.getParsedJSON().get("meanVector");

        double expectedValueAtRace = 0.8773148148148148;
        assertTrue(Math.abs(meanVector.get("race") - expectedValueAtRace) < 1e-6);
    }

    @Test
    void getCumulativeBaselineModel() {
        HashMap<Double, Double> cumulativeBaselineModel = this.deserializeJSON.getCumulativeBaselineModel();

        double expectedValueAt7 = 0.01381023231607111;
        assertTrue(Math.abs(cumulativeBaselineModel.get(7.) - expectedValueAt7) < 1e-6);

        double expectedValueAt39 = 0.18303689843676504;
        assertTrue(Math.abs(cumulativeBaselineModel.get(39.) - expectedValueAt39) < 1e-6);
    }

    @Test
    void throwsErrorThatIsCaught(){
        DeserializeJSON deserializeJSON= new DeserializeJSON("error");
    }
}