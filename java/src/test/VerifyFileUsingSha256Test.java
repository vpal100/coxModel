package test;

import com.company.VerifyFileUsingSha256;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VerifyFileUsingSha256Test {

    @Test
    void verifyHashAndReturnFile() {
        //A file with its correct hash
        String readFile = new VerifyFileUsingSha256("cph_model_exports.json", "sha256.hash").verifyHashAndReturnFile();
        assertTrue(readFile.length() > 10);

        //file does not exist
        String notAFile = new VerifyFileUsingSha256("notAFile", "sha256.hash").verifyHashAndReturnFile();
        assertNull(notAFile);

        //incorrect hash
        String incorrectHash = new VerifyFileUsingSha256("cph_model_exports.json", "incorrectSha256.hash").verifyHashAndReturnFile();
        assertNull(incorrectHash);
    }
}