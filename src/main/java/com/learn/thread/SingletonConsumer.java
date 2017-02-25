package com.learn.thread;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.apache.log4j.Logger;

/**
 * Created by searene on 2/25/17.
 */
public class SingletonConsumer {
    private final static Logger logger = Logger.getLogger(XsiNilLoader.Single.class);
    private static SingletonConsumer instance;
    private SingletonConsumer(){}

    private static void simulateconstructionTime() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static SingletonConsumer getSingletonConsumer() {
        if(instance == null) {
            synchronized (SingletonConsumer.class) {
                if (instance == null) {
                    logger.debug("instance is null, trying to instantiate a new one");
                    simulateconstructionTime();
                    instance = new SingletonConsumer();
                } else {
                    logger.debug("instance is not null, return the already-instantiated one");
                }
            }
        }
        return instance;
    }
}
