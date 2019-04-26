package com.net.util.esc;

import com.net.util.de.EscPosWebPrinter;
import com.sun.xml.internal.txw2.output.DataWriter;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/*
 *@Author BieFeNg
 *@Date 2019/4/20 16:27
 *@DESC
 */
public class ESCPrint {

    private Socket socket;

    private OutputStream os;

    private static final int DEFAULT_PORT = 9100;

    private OutputStreamWriter writer;

    public ESCPrint(String ip) {
        try {
            socket = new Socket(ip, DEFAULT_PORT);
            os = socket.getOutputStream();
            writer = new OutputStreamWriter(os, "gbk");
        } catch (IOException e) {
            throw new RuntimeException("*******************打印机连接失败*********************");
        }
    }

    public void test() throws IOException {
        writer.write(0x1B);
        writer.write(0x40);
        writer.flush();

        os.write(27);
        os.write(52);

        writer.write("你好");
        writer.write(27);
        writer.write(100);
        writer.write(3);
        writer.write(10);
        writer.flush();

        os.write(27);
        os.write(0x69);

        /*        writer.write(4);
         */

        writer.flush();

    }

    public static void main(String[] args) throws IOException {
        String ip = "10.128.38.87";
      /*  EscPosWebPrinter posWebPrinter = new EscPosWebPrinter(ip,9100);
        posWebPrinter.init();
        posWebPrinter.printStr("你好");
        posWebPrinter.feedAndCut();*/
        ESCPrint print = new ESCPrint(ip);

        print.test();
    }
}
