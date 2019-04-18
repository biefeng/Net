package zpl;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/*
 *@Author BieFeNg
 *@Date 2019/4/17 14:15
 *@DESC
 */
public class DPPrint {


    public static void main(String[] args) throws IOException {
        InputStream ins = new FileInputStream("C:\\Log Files\\Intermec PC43d (203 dpi)\\文档1.btw.prn");
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("10.0.0.71",9100));
        OutputStream os = socket.getOutputStream();

        BufferedOutputStream baos = new BufferedOutputStream(os);
       /*baos.write("NASC 8\r\npp110,140\r\nFONTD \"MHeiGB18030C-Medium\",8,20,100\r\nPT \"雀巢咖啡1111111111111\r\nPF".getBytes());
       baos.flush();*/
         int len = 0;
        byte[] bytes = new byte[2048];
        while ((len = ins.read(bytes)) != -1) {
            baos.write(bytes,0,len);
        }
        baos.flush();
        baos.close();
        socket.close();

    }

    public void test1() throws IOException {
        /*InputStream ins = new FileInputStream("d:/doc/testHex.txt");

        OutputStream os = new FileOutputStream("d:/doc/testHex_1.txt");

        StringBuffer stringBuffer = new StringBuffer();
        int len = 0;
        byte[] bytes = new byte[2048];

        try (BufferedOutputStream bos = new BufferedOutputStream(os); ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            while ((len = ins.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            baos.flush();
            byte[] bytes1 = baos.toByteArray();
            System.out.println(new String(bytes));
        }*/
    }
}
