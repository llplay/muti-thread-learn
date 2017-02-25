package com.learn;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by searene on 2/24/17.
 */
public class MultiThreadDemoTest {

    private final static Logger logger = Logger.getLogger(MultiThreadDemoTest.class);

    private String inputFile = FileUtil.getResourceFile("words.txt");
    private String outputFile = FileUtil.getResourceFile("encoded_words.txt");

    private Starter starter = new Starter();

    private boolean isFileDataSame(String file1, String file2) throws IOException {
        String input = new String(Files.readAllBytes(Paths.get(file1)));
        String output = new String(Files.readAllBytes(Paths.get(file1)));
        Set<String> set1 = new HashSet<>(Arrays.asList(input.split("\n")));
        Set<String> set2 = new HashSet<>(Arrays.asList(output.split("\n")));

        return set1.equals(set2);
    }

    @Test
    public void singleThreadTest() throws IOException {
        starter.startSingleThreadTask(inputFile, outputFile);
        Assert.assertTrue(isFileDataSame(inputFile, outputFile));
    }

    @Test
    public void multiThreadTest() throws IOException {
        starter.startMultiThreadTask(inputFile, outputFile);
        Assert.assertTrue(isFileDataSame(inputFile, outputFile));
    }

    @Test
    public void simpleTest() {
        logger.info(FileUtil.getResourceFile("test"));
    }


}
