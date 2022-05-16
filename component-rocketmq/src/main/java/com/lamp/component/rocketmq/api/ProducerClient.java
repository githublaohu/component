package com.lamp.component.rocketmq.api;

import com.lamp.component.rocketmq.SendConfig;

public interface ProducerClient {
    

    public void sendOneway(Object sendObjct , SendConfig sendConfig) throws Exception ;

    public void send(Object sendObjct,  SendConfig sendConfig) throws Exception;
    
    public void sendAsyn(Object sendObjct, SendConfig sendConfig);
    
}
