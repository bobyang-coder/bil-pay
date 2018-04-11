package com.bob.mq.kafka.demo;

/**
 * Created by bob on 2017/9/17.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/9/17
 */
public class KafkaProperties {
    public static final String TOPIC = "test1";
    public static final String KAFKA_SERVER_URL = "185.207.177.45";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";

    private KafkaProperties() {}
}
