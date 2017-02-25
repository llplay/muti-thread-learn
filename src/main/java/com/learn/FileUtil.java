package com.learn;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Created by searene on 2/24/17.
 */
public class FileUtil {

    private final static Logger logger = Logger.getLogger(FileUtil.class);

    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    private int readLineCount;

    public static void clearFileContents(String filePath) {
        File file = new File(filePath);
        try {
            PrintWriter writer = null;
            writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public FileUtil(String inputFile) {
        try {

            fileWriter = new FileWriter(inputFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendToFile(String line) {
        printWriter.println(line);
    }

    public static void readFileLineByLine(String filePath, Consumer<String> consumer) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1252"));
            String line;
            while ((line = br.readLine()) != null) {
                consumer.accept(line);
            }
            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    public static String getResourceFile(String fileName) {
        URL url = FileUtil.class.getClassLoader().getResource(fileName);
        return url.toString().substring(5);
    }

    public void flushAndClose() {
        try {
            // flush all
            printWriter.flush();
            bufferedWriter.flush();
            fileWriter.flush();

            // flushAndClose all
            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String processLine(String line) throws IOException {
        int min = 1;
        int max = 100;
        int randomMillisecconds = min + (int)(Math.random() * ((max - min) + 1));
        try {
            Thread.sleep(randomMillisecconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // readLineCount is not accurate in multi-thread mode
        readLineCount++;
        logger.info(String.format("%d lines were read.", readLineCount));
        return line;
    }
}
