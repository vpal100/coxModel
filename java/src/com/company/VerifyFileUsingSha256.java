package com.company;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public String verifyHashAndReturnFile() throws Exception {
        String sha256HashOfFile;
        String sha256HashFromFile;
        String path = Objects.requireNonNull(Main.class.getClassLoader().getResource(this.fileName)).getPath();
        logger.log(Level.INFO, "Path for the file: " + path);
        String pathForHash = Objects.requireNonNull(Main.class.getClassLoader().getResource(this.fileNameForSha256)).getPath();
        logger.log(Level.INFO, "Path for the hash: "+ pathForHash);
        try {
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
        catch (IOException exception) {
            logger.log(Level.SEVERE, "The File was not found.");
            exception.printStackTrace();
        }
        throw new Exception("The hash of the file does not match the stored hash.");
    }
}
