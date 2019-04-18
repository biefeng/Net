package com.net.util.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;

/*
 *@Author BieFeNg
 *@Date 2019/3/12 11:36
 *@DESC
 */
public class CuratorMain {

    public static void main(String[] args) throws Exception {
        CuratorMain main = new CuratorMain();
        main.getConnector();
    }

    public void getConnector() throws Exception {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", policy);
        client.start();
        byte[] data = new byte[2048];
        byte[] bytes = client.getData().forPath("/");
        System.out.println("num: "+bytes.length);
        client.close();
        client.setData().forPath("");

    }
}
