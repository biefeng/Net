package com.net.netty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
 *@Author BieFeNg
 *@Date 2019/3/28 15:08
 *@DESC
 */
public class NettyTest {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8388);
        OutputStream outputStream = socket.getOutputStream();
        while (socket.isConnected()){
            outputStream.write("Hello".getBytes());
            break;
        }
        outputStream.flush();
        socket.shutdownOutput();
        InputStream is = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len =  0;
        while ((len=is.read(bytes))!=-1){
            System.out.println(new String(bytes,0,len));
        }
        socket.close();
    }
}
