package com.company;

import org.apache.commons.codec.digest.DigestUtils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class VerifyFileUsingSha256 {
    private final String fileName;
    private final String fileNameForSha256;
    private static final Logger logger = Logger.getLogger(VerifyFileUsingSha256.class.getName());

    public VerifyFileUsingSha256(String fileName, String fileNameForSha256){
        this.fileName = fileName;
        this.fileNameForSha256 = fileNameForSha256;
    }

    /**
     * Given a file and its SHA256 hash, it verifies that the hash is correct.
     * @return The file after the hash is verified or null if there is an error or the hashes do not match.
     *         The handling of the exceptions needs to be determined by the use cases, and whether a default/backup model should be used.
     */
    public String verifyHashAndReturnFile() {
        String sha256HashOfFile;
        String sha256HashFromFile;
        try {
            String path = Objects.requireNonNull(Main.class.getClassLoader().getResource(this.fileName)).getPath();
            logger.log(Level.INFO, "Path for the file: " + path);
            String pathForHash = Objects.requireNonNull(Main.class.getClassLoader().getResource(this.fileNameForSha256)).getPath();
            logger.log(Level.INFO, "Path for the hash: "+ pathForHash);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String fileAsString = bufferedReader.lines().collect(Collectors.joining());
            sha256HashOfFile = DigestUtils.sha256Hex(fileAsString);

            BufferedReader bufferedReaderForHash = new BufferedReader(new FileReader(pathForHash));
            sha256HashFromFile = bufferedReaderForHash.lines().collect(Collectors.joining());
            if(sha256HashFromFile.equals( sha256HashOfFile) ){
                logger.log(Level.INFO, "The hash of the file matches the stored hash.");
                return fileAsString;
            }
        }
        catch (NullPointerException | FileNotFoundException exception ) {
            logger.log(Level.SEVERE, "The File was not found.");
            exception.printStackTrace();
            return null;
        }
        logger.log(Level.SEVERE,"The hash of the file does not match the stored hash.");
        return null;
    }
}
