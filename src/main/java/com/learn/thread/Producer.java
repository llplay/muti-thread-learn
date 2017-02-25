package com.learn.thread;

import com.learn.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by searene on 2/24/17.
 */
public class Producer implements Runnable {

    private LinkedBlockingQueue<String> queue;

    private String inputFile;

    private List<Thread> consumerThreadList = new ArrayList<>();

    public Producer(LinkedBlockingQueue<String> queue, String inputFile) {
        this.queue = queue;
        this.inputFile = inputFile;
    }

    public void addConsumer(Thread consumerThread) {
        consumerThreadList.add(consumerThread);
    }

    @Override
    public void run() {
        FileUtil.readFileLineByLine(inputFile, line -> {
            queue.add(line);
        });

        // wait for 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Thread consumerThread: consumerThreadList) {
            consumerThread.interrupt();
        }
    }
}
