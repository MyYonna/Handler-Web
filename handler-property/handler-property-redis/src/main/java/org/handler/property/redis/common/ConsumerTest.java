package org.handler.property.redis.common;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

public class ConsumerTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_handler_propety_consumer");// 您在MQ控制台创建的Consumer ID
	       properties.put(PropertyKeyConst.AccessKey, "LTAIzHGJ8F7VcZcn");
	       properties.put(PropertyKeyConst.SecretKey, "H3DyRUDL5nmZ0p9LVaqTJyAeJx4Ot9");
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("handler_property", "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                System.out.println("Receive: " + message);
                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }
}