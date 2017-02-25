package com.learn.thread;

import com.learn.FileUtil;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by searene on 2/24/17.
 */
public class Producer implements Runnable {

    private LinkedBlockingQueue<Optional<String>> queue;

    private String inputFile;

    public Producer(LinkedBlockingQueue<Optional<String>> queue, String inputFile) {
        this.queue = queue;
        this.inputFile = inputFile;
    }

    @Override
    public void run() {
        FileUtil.readFileLineByLine(inputFile, line -> {
            queue.add(Optional.of(line));
        });
        for(int i = 0; i < Consumer.consumerThreadCount; i++) {
            queue.add(Optional.empty());
        }
    }
}
