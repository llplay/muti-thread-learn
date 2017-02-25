package com.learn;

import com.learn.thread.Consumer;
import com.learn.thread.Producer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by searene on 2/24/17.
 */
public class Starter {
    private final static Logger logger = Logger.getLogger(Starter.class);

    public void startMultiThreadTask(String inputFile, String outputFile) throws IOException {

        LocalDateTime startTime = LocalDateTime.now();

        List<Thread> threads = new ArrayList<>();
        LinkedBlockingQueue<Optional<String>> queue = new LinkedBlockingQueue<>();

        FileUtil.clearFileContents(outputFile);

        Producer producer = new Producer(queue, inputFile);
        threads.add(new Thread(producer));

        Consumer consumer = new Consumer(queue, outputFile);
        for(int i = 0; i < Consumer.consumerThreadCount; i++) {
            threads.add(new Thread(consumer));
        }

        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        consumer.getFileUtil().flushAndClose();

        // get consumer's totalCount:
        logger.debug("Consumer's totalCount: " + consumer.getTotalCount());

        LocalDateTime endTime = LocalDateTime.now();
        logger.info(String.format("It takes %s seconds to finish", LocalDateTime.from(startTime).until(endTime, ChronoUnit.SECONDS)));
    }

    public void startSingleThreadTask(String inputFile, String outputFile) {
        LocalDateTime startTime = LocalDateTime.now();
        FileUtil fileUtil = new FileUtil(outputFile);
        FileUtil.clearFileContents(outputFile);
        FileUtil.readFileLineByLine(inputFile, line -> {
            String processedLine = null;
            try {
                processedLine = fileUtil.processLine(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileUtil.appendToFile(processedLine);
        });
        fileUtil.flushAndClose();
        LocalDateTime endTime = LocalDateTime.now();
        logger.info(String.format("It takes %s seconds to finish", LocalDateTime.from(startTime).until(endTime, ChronoUnit.SECONDS)));
    }
}
