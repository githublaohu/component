package com.lamp.component.rocketmq.api;

import java.lang.reflect.InvocationTargetException;

import com.lamp.component.rocketmq.SendConfig;

public abstract class AbstractProducerClient  implements ProducerClient{

    abstract Object createMessage(Object sendObjct ,SendConfig sendConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
