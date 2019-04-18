package com.net;

import com.google.common.collect.Lists;
import com.net.util.StringUtils;
import org.junit.Test;
import pcl.ImagePixelUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/*
 *@Author BieFeNg
 *@Date 2019/4/12 19:34
 *@DESC
 */
public class TestStr {

    @Test
    public void test2() {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("d:/test_3.pcl"));
            byte[] bytes = StringUtils.fromDecStrtoByteArr("27 37 45 49 50 51 52 53 88 64 80 74 76 32 13 10 64 80 74 76 32 67 79 77 77 69 78 84 32 66 101 103 105 110 110 105 110 103 32 80 111 115 116 83 99 114 105 112 116 32 74 111 98 13 10 64 80 74 76 32 69 78 84 69 82 32 76 65 78 71 85 65 71 69 32 61 32 80 67 76 13 10 27 69 27 42 112 51 48 48 120 52 48 48 89 27 42 114 48 70 27 42 116 55 53 82 27 42 114 49 50 53 84 27 42 114 49 50 49 83 27 42 114 49 65 27 42 98 48 89 27 42 98 48 77 27 42 98 52 87 0 0 -128 0 27 42 98 52 87 0 0 -64 0 27 42 98 52 87 0 0 -32 0 27 42 98 52 87 0 0 -16 0 27 42 98 52 87 0 0 -8 0 27 42 98 52 87 0 0 -4 0 27 42 98 52 87 0 0 -2 0 27 42 98 52 87 0 0 -1 0 27 42 98 52 87 0 0 -1 -128 27 42 98 52 87 -1 -1 -1 -64 27 42 98 52 87 -1 -1 -1 -32 27 42 98 52 87 -1 -1 -1 -16 27 42 98 52 87 -1 -1 -1 -8 27 42 98 52 87 -1 -1 -1 -4 27 42 98 52 87 -1 -1 -1 -2 27 42 98 52 87 -1 -1 -1 -1 27 42 98 52 87 -1 -1 -1 -2 27 42 98 52 87 -1 -1 -1 -4 27 42 98 52 87 -1 -1 -1 -8 27 42 98 52 87 -1 -1 -1 -16 27 42 98 52 87 -1 -1 -1 -32 27 42 98 52 87 -1 -1 -1 -64 27 42 98 52 87 0 0 -1 -128 27 42 98 52 87 0 0 -1 0 27 42 98 52 87 0 0 -2 0 27 42 98 52 87 0 0 -4 0 27 42 98 52 87 0 0 -8 0 27 42 98 52 87 0 0 -16 0 27 42 98 52 87 0 0 -32 0 27 42 98 52 87 0 0 -64 0 27 42 98 52 87 0 0 -128 0 27 37 45 49 50 51 52 53 88");

            bos.write(bytes);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void test3() {
        try {
            FileInputStream fis = new FileInputStream("d:/test_2.pcl");
            int len = 0;
            byte[] bytes = new byte[2048];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = fis.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            String uelS = "1B 25 2D  31 32 33 34 35 58";
            String b4w = "1B 2A 62 34 57";

            byte[] bytes1 = StringUtils.fromHexStrtoByteArr(b4w);

           /* BufferedImage image = ImageIO.read(new File("d:/doc/t11.bmp"));
            WritableRaster raster = image.getRaster();
            DataBuffer dataBuffer = raster.getDataBuffer();
            byte[] data = ((DataBufferByte) dataBuffer).getData();


            int index = 0;
            for (byte bb : data) {
                if (index == 0)
                    baos.write(bytes1);
                if (index++ < 4) {
                    baos.write(bb);
                } else {
                    index = 0;
                }
            }*/
            /*baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(128);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(192);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(224);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(240);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(248);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(252);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(254);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(255);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(255);
            baos.write(128);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(192);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(224);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(240);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(248);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(252);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(254);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(254);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(252);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(248);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(240);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(224);
            baos.write(bytes1);
            baos.write(255);
            baos.write(255);
            baos.write(255);
            baos.write(192);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(255);
            baos.write(128);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(255);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(254);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(252);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(248);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(240);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(224);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(192);
            baos.write(0);
            baos.write(bytes1);
            baos.write(0);
            baos.write(0);
            baos.write(128);
            baos.write(0);*/


            byte[] bytes2 = StringUtils.fromHexStrtoByteArr(uelS);
            baos.write(bytes2);

            byte[] arr = baos.toByteArray();
            for (byte b : arr) {
                System.out.print(b + " ");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\BieFeNg\\AppData\\Local\\Temp\\x7qj6oxgevwe5xk9pynjt6gviqgu4o850128vjewsin72pgb9\\1-test_3.png"));
            WritableRaster raster = image.getRaster();
            DataBuffer dataBuffer = raster.getDataBuffer();
            byte[] data = ((DataBufferByte) dataBuffer).getData();

            System.out.println(raster);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getData(BufferedImage srcImage) {
        try {

            BufferedImage image = ImagePixelUtil._24to1bit(srcImage);
            WritableRaster raster = image.getRaster();
            DataBuffer dataBuffer = raster.getDataBuffer();
            byte[] data = ((DataBufferByte) dataBuffer).getData();
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void test5() {
        try {
            BufferedImage read = ImageIO.read(new File("d:/doc/test.jpg"));
            getData(read);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void name6() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("10.0.0.70",9100));
        OutputStream outputStream = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter( outputStream);
        writer.write("nasc 8\r\n");
        writer.write("PRPOS 20,50\r\n");
        writer.write("FONTD \"FZShuSong-Z01S\",10");
        writer.write("PT \"备注：\"");
        writer.write("PF");
        writer.flush();
        writer.close();
        socket.close();
    }
}
