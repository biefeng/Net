package com.net.proxy;

/*
 *@Author BieFeNg
 *@Date 2019/3/21 16:05
 *@DESC
 */
public class Consumer implements IConsumer {
    @Override
    public void eat() {
        System.out.println("eating");
        throw new RuntimeException("cry");
    }
}
