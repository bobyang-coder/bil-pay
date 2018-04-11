package com.bob.mq.kafka.helloworld;


import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * kafka消息生产者
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/9/17
 */
public class Producer extends Thread {

    private String topic;

    public Producer(String topic) {
        super();
        this.topic = topic;
    }

    @Override
    public void run() {
        KafkaProducer producer = createProducer();
        for (int i = 0; i < 100; i++) {
            Future send = producer.send(new ProducerRecord<String, String>(topic, Integer.toString(i), Integer.toString(i)),
                    new Callback() {
                        public void onCompletion(RecordMetadata metadata, Exception e) {
                            if (e != null)
                                System.out.println("获取消息发送结果");
                            e.printStackTrace();
                        }
                    });
            System.out.println(send);
            System.out.println("Message sent successfully");
        }
        producer.close();
    }

    private KafkaProducer createProducer() {
        Map props = new HashMap<>();
        props.put("bootstrap.servers", "185.207.177.45:9092");// 声明kafka broker
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 0);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 10);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer(props);
    }

    public static void main(String[] args) {
        new Producer("bob").start();// 使用kafka集群中创建好的主题 test
    }
}
