package com.lamp.component.rocketmq.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import com.lamp.component.rocketmq.SendConfig;

public class RocketMQProducerClient extends AbstractProducerClient{

    private DefaultMQProducer defaultMQProducer;
    
     Message createMessage(Object object ,SendConfig sendConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Message message = new Message();
        message.setTags(sendConfig.getTopic());
        if(Objects.nonNull(sendConfig.getKey())) {
            message.setKeys((String)sendConfig.getKey().invoke(object));
        }
        if(Objects.nonNull(sendConfig.getTag())) {
            message.setKeys((String)sendConfig.getTag().invoke(object));
        }
        message.setBody(sendConfig.getSerialize().serialize(object));
        
        return message;
    }

    public void sendOneway(Object sendObjct) {
        
    }
    public void sendOneway(Object sendObjct, SendConfig sendConfig) throws Exception {
        defaultMQProducer.sendOneway(createMessage(sendObjct, sendConfig));
        
    }

    public void send(Object sendObjct, SendConfig sendConfig) throws Exception {
        SendResult sendResult = defaultMQProducer.send(createMessage(sendObjct, sendConfig));
    }

    public void sendAsyn(Object sendObjct, SendConfig sendConfig) {
       // defaultMQProducer.send(msgs)
        
    }
}
