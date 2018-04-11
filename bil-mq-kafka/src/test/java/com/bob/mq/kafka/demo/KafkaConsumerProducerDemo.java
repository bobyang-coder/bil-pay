package com.bob.mq.kafka.demo;

/**
 * Created by bob on 2017/9/17.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/9/17
 */
public class KafkaConsumerProducerDemo {
    public static void main(String[] args) {
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
        Producer producerThread = new Producer(KafkaProperties.TOPIC, isAsync);
        producerThread.start();

        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
        consumerThread.start();

    }
}
