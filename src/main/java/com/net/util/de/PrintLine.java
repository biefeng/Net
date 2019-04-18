/*
package com.net.util.de;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * @author Btboy1978 QQ: 138027869
 * @ POS小票打印机  网口无驱打印
 * <p>
 * 调用方法  new PrintLine().print(list,map,string);
 *//*

public class PrintLine {
    static String PRINT_IP = "10.128.36.199";
    static int PRINT_PORT = 9100;
    Socket socket = null;
    OutputStream socketOut = null;
    OutputStreamWriter writer = null;
    String[] Colum_Name = new String[]{"菜名", "份数", "小计金额"};// 设定小票列标题

    */
/**
     * @throws IOException
     *//*

    public PrintLine() throws IOException {
        // TODO Auto-generated constructor stub
        // 建立打印机连接
        //
        socket = new Socket(PRINT_IP, PRINT_PORT);
        socketOut = socket.getOutputStream();
        writer = new OutputStreamWriter(socketOut, "GBK");
    }

    public static void main(String[] args) throws IOException {
        //小票页面 公司信息
        Map<String, String> GS_INFO = new HashMap<String, String>();
        GS_INFO.put("GS_Name", "锦益科技公司");
        GS_INFO.put("GS_Address", "光谷APP广场3栋702");
        GS_INFO.put("GS_Tel", "13476268115");
        GS_INFO.put("GS_Qq", "821697023");

        //菜品信息
        List<Map<String, Object>> PRINT_DATA = new ArrayList<Map<String, Object>>();
        Map<String, Object> data1 = new HashMap<String, Object>();
        data1.put("cai_name", "小鸡炖蘑菇");
        data1.put("cai_price", "36");
        PRINT_DATA.add(data1);


        PrintLine printLine = new PrintLine();

        printLine.print(PRINT_DATA, GS_INFO, "123456789");
    }

    */
/**
     * @param PRINT_DATA 小票主要数据
     * @param GS_INFO    小票附带信息
     * @param CAIDAN_SN  小票号码
     *//*

    public void print(List<Map<String, Object>> PRINT_DATA, Map<String, String> GS_INFO, String CAIDAN_SN) {

        try {
            // 条码打印指令
            byte[] PRINT_CODE = new byte[9];
            */
/*PRINT_CODE[0] = 0x1d;
            PRINT_CODE[1] = 0x68;
            PRINT_CODE[2] = 120;
            PRINT_CODE[3] = 0x1d;
            PRINT_CODE[4] = 0x48;
            PRINT_CODE[5] = 0x10;
            PRINT_CODE[6] = 0x1d;
            PRINT_CODE[7] = 0x6B;
            PRINT_CODE[8] = 0x02;*//*

            */
/*选择类型*//*

            PRINT_CODE[0] = 0x1D;
            PRINT_CODE[1] = 0x5A;
            PRINT_CODE[2] = 0x02;
            */
/*打印*//*

            PRINT_CODE[0] = 0x1B;
            PRINT_CODE[1] = 0x5A;
            PRINT_CODE[2] = 0x01;//列数


            PRINT_CODE[0] = 0x04;//纠错等级
            PRINT_CODE[1] = 0x03;//长宽比

            */
/*48 65 6C 6C 6F*//*

            PRINT_CODE[2] = 0x05;//低位字节
            PRINT_CODE[2] = 0x00;//高位字节
            PRINT_CODE[2] = 0x00;//字符版本

            PRINT_CODE[2] = 0x48;
            PRINT_CODE[2] = 0x65;
            PRINT_CODE[2] = 0x6C;
            PRINT_CODE[2] = 0x6C;
            PRINT_CODE[2] = 0x6F;


            // 清除字体放大指令
            byte[] FD_FONT = new byte[3];
            FD_FONT[0] = 0x1c;
            FD_FONT[1] = 0x21;
            FD_FONT[2] = 4;
            // 字体加粗指令
            byte[] FONT_B = new byte[3];
            FONT_B[0] = 27;
            FONT_B[1] = 33;
            FONT_B[2] = 8;
            // 字体纵向放大一倍
            byte[] CLEAR_FONT = new byte[3];
            CLEAR_FONT[0] = 0x1c;
            CLEAR_FONT[1] = 0x21;
            CLEAR_FONT[2] = 0;
            // 计算合计金额
            int price = 0;
            //socketOut.write(PRINT_CODE);
            socketOut.write(27);
            socketOut.write(64);
            socketOut.write(FD_FONT);// 字体放大
            socketOut.write(FONT_B);// 字体加粗
            socketOut.write(10);
            final String blankStr = "                     ";
            writer.write("桌号：  " + 18);
            writer.flush();// 关键,很重要,不然指令一次性输出,后面指令覆盖前面指令,导致取消放大指令无效
            socketOut.write(CLEAR_FONT);

            socketOut.write(10);
            writer.write("------------------------------------------------\r\n");
            writer.write(alignStr("任务总数：", "1/3", 20) + " \r\n");
            writer.write(alignStr("下单时间：", LocalDateTime.now().toString(), 20) + "\r\n");
            writer.write(alignStr("单据号：", "1100051300000007616", 20) + " \r\n");
            writer.write("------------------------------------------------\r\n");
            writer.write(alignStr("品名", "数量", 30) + " \r\n");
            writer.write(alignStr("青椒肉丝盛大的(打包）", "1", 31) + " \r\n");//小括号用String.length取时占了2位，实际打印也是2位，而不应该是4

            */
/*writer.write(Fix_String_Lenth(1, Colum_Name[0], 14)
                    + Fix_String_Lenth(0, Colum_Name[1], 4)
                    + Fix_String_Lenth(1, Colum_Name[2], 6) + "\r\n");
            for (int i = 0; i < PRINT_DATA.size(); i++) {
                writer.write(Fix_String_Lenth(0, PRINT_DATA.get(i).get("cai_name").toString(), 14)
                        + Fix_String_Lenth(1, 1 + "", 4)
                        + Fix_String_Lenth(1, PRINT_DATA.get(i).get("cai_price").toString(), 6) + "\r\n");
                price += Integer.parseInt(PRINT_DATA.get(i).get("cai_price").toString());
            }*//*

            writer.write("------------------------------------------------");
            writer.flush();
            socketOut.write(FD_FONT);// 字体放大
            socketOut.write(FONT_B);// 字体加粗
            socketOut.write(10);
            writer.write("备注：不许再买了");
            writer.flush();// 关键,很重要,不然指令一次性输出,后面指令覆盖前面指令,导致取消放大指令无效
            socketOut.write(CLEAR_FONT);
            socketOut.write(10);
            // 下面指令为打印完成后自动走纸
            */
/*writer.write(27);
            writer.write(100);
            writer.write(4);
            writer.write(10);
*//*

            // TODO: 2017/2/18 这里应该还要加入 自动切纸 的命令
            writer.flush();
            EscPosWebPrinter printer = new EscPosWebPrinter(socketOut, writer);

            printer.printQR("1111111111111111111111111111111111111111111111", 48, 11);
            //printer.printQR("111", 48, 11);
            writer.flush();
            printer.feedAndCut();

            writer.close();
            socketOut.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    */
/**
     * 左对齐字符串
     *
     * @param str1
     * @param str2
     * @param lenght 左间距
     * @return
     *//*

    public String alignStr(String str1, String str2, int lenght) {
        int len1 = str1.length() * 2;
        ;

        StringBuffer sb = new StringBuffer();

        sb.append(str1);
        for (; len1++ < lenght; ) {
            sb.append(" ");
        }
        sb.append(str2);

        return sb.toString();
    }

    */
/**
     * 字符串长度补齐,方便打印时对齐,美化打印页面,不过中文计算好像有点问题
     *
     * @param strs 源字符
     * @param len  指定字符长度
     * @return
     * @throws UnsupportedEncodingException
     *//*

    public String Fix_String_Lenth(int type, String strs, int len) {
        String strtemp = strs;
        int len1 = strs.length();
        switch (type) {
            case 0:
                while (strtemp.length() < len) {
                    strtemp += " ";
                }
                break;
            case 1:
                while (strtemp.length() < len) {
                    strtemp += " ";
                }
                break;
            default:

                break;
        }
        return strtemp;
    }

    */
/**
     * 打印图片
     * <p>
     * //@param path 图片地址
     *
     * @return
     *//*

    */
/*private void image1(String path) throws IOException {
        // trans to byte array
        Bitmap bmp = BitmapFactory.decodeFile(path);

        BufferedImage image = ImageIO.read(new File("D:\\doc\\test.jpg"));
        int[] rgb = image.getRGB(0, 0, image.getWidth(), image.getHeight(), new int[image.getWidth() * image.getHeight()], 0,
                image.getWidth());


        byte[] data = new byte[]{0x1B, 0x33, 0x00};
        write(data);
        data[0] = (byte) 0x00;
        data[1] = (byte) 0x00;
        data[2] = (byte) 0x00;    //重置参数

        int pixelColor;

        // ESC * m nL nH 点阵图
        byte[] escBmp = new byte[]{0x1B, 0x2A, 0x00, 0x00, 0x00};

        escBmp[2] = (byte) 0x21;

        //nL, nH
        escBmp[3] = (byte) (bmp.getWidth() % 256);
        escBmp[4] = (byte) (bmp.getWidth() / 256);

        // 每行进行打印
        for (int i = 0; i < bmp.getHeight() / 24 + 1; i++) {
            write(escBmp);

            for (int j = 0; j < bmp.getWidth(); j++) {
                for (int k = 0; k < 24; k++) {
                    if (((i * 24) + k) < bmp.getHeight()) {

                        pixelColor = rgb[]
                        if (pixelColor != -1) {
                            data[k / 8] += (byte) (128 >> (k % 8));
                        }
                    }
                }

                write(data);
                // 重置参数
                data[0] = (byte) 0x00;
                data[1] = (byte) 0x00;
                data[2] = (byte) 0x00;
            }
            //换行
            byte[] byte_send1 = new byte[2];
            byte_send1[0] = 0x0d;
            byte_send1[1] = 0x0a;
            write(byte_send1);
        }

    }*//*

    public void image() throws IOException {
        BufferedImage image = ImageIO.read(new File("D:\\doc\\t11.png"));
        int[] pixels = image.getData().getPixels(0, 0, image.getWidth(), image.getHeight(), new int[image.getWidth() * image.getHeight() * 3]);
        char[] cs = {'你', '好', '呀'};
        int ics = 0;

        byte[] data = new byte[]{0x1B, 0x33, 0x00};
        write(data);
        data[0] = (byte) 0x00;
        data[1] = (byte) 0x00;
        data[2] = (byte) 0x00;    //重置参数

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int off = i * image.getWidth() + j;
                if (pixels[off] == 0) {
                    data[ics] = 1;
                    System.out.print(cs[ics]);
                    ics = (ics + 1) % 3;
                } else {
                    System.out.print("　");
                }

                if (j == image.getWidth() - 1) {
                    System.out.println();
                }
            }
            write(data);
            // 重置参数
            data[0] = (byte) 0x00;
            data[1] = (byte) 0x00;
            data[2] = (byte) 0x00;
        }
        //换行
        byte[] byte_send1 = new byte[2];
        byte_send1[0] = 0x0d;
        byte_send1[1] = 0x0a;
        write(byte_send1);
    }

    private void write(byte... data) throws IOException {
        socketOut.write(data);
        socketOut.flush();
    }

}
*/
