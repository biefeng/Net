package com.net.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/*
 *@Author BieFeNg
 *@Date 2019/3/21 15:59
 *@DESC
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Transaction begin");
        Object obj = null;
        try {
            obj = method.invoke(target, args);
        } catch (Exception e) {
            System.out.println("Transaction rollback");
        }
        System.out.println("Transaction commit");
        return obj;
    }
}
