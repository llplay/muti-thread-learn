package com.learn.thread;

import com.learn.FileUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Optional;
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

    private LinkedBlockingQueue<Optional<String>> queue;

    public Consumer(LinkedBlockingQueue<Optional<String>> queue, String outputFile) {
        this.queue = queue;
        this.fileUtil = new FileUtil(outputFile);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Optional<String> line = queue.take();
                if (!line.isPresent()) break;
                totalCount.incrementAndGet();
                String processedLine = fileUtil.processLine(line.get());
                fileUtil.appendToFile(processedLine);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
