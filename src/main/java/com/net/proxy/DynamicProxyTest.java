package com.net.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/*
 *@Author BieFeNg
 *@Date 2019/3/21 16:02
 *@DESC
 */
public class DynamicProxyTest {
    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        InvocationHandler invocationHandler = new MyInvocationHandler(consumer);
        IConsumer proxy= (IConsumer) Proxy.newProxyInstance(Consumer.class.getClassLoader(), Consumer.class.getInterfaces(), invocationHandler);
        proxy.eat();




    }
}
