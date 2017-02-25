package com.learn.thread;

import com.learn.FileUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by searene on 2/24/17.
 */
public class Consumer implements Runnable {

    private AtomicInteger totalCount = new AtomicInteger(0);

    public static final int consumerThreadCount = 10;

    private FileUtil fileUtil;

    private final static Logger logger = Logger.getLogger(Consumer.class);

    private LinkedBlockingQueue<String> queue;

    public Consumer(LinkedBlockingQueue<String> queue, String outputFile) {
        this.queue = queue;
        this.fileUtil = new FileUtil(outputFile);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String line = null;
                try {
                    line = queue.take();
                } catch(InterruptedException e) {
                    logger.warn(String.format("%s was interrupted, exiting...", Thread.currentThread()));
                    break;
                }
                totalCount.incrementAndGet();
                String processedLine = fileUtil.processLine(line);
                fileUtil.appendToFile(processedLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileUtil getFileUtil() {
        return fileUtil;
    }

    public int getTotalCount() {
        return totalCount.get();
    }
}
