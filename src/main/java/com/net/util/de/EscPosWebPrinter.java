package com.net.util.de;

import com.net.util.bitmap.ImagePixelUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * @author Tony
 */
public class EscPosWebPrinter {

    private OutputStream socketOut = null;
    private OutputStreamWriter writer = null;


    private static final String ENCODING = "GBK";

    /**
     * init ESC POS printer device which support network printing
     *
     * @param printerIp
     * @param port      normally 9100
     * @throws IOException
     */
    public EscPosWebPrinter(String printerIp, int port) throws IOException {

        Socket socket = new Socket(printerIp, port);
        socket.setSoTimeout(1000);
        socketOut = socket.getOutputStream();

        writer = new OutputStreamWriter(socketOut, "GBK");
        socketOut.write(27);
        socketOut.write(27);
    }

    public EscPosWebPrinter(OutputStream socketOut, OutputStreamWriter writer) {
        this.socketOut = socketOut;
        this.writer = writer;
    }

    /**
     * resets all writer settings to default
     */
    public void resetToDefault() throws IOException {
        setInverse(false);
        setBold(false);
        setFontDefault();
        setUnderline(0);
        setJustification(0);
        writer.flush();
    }

    /**
     * Sets bold
     */
    public void setBold(Boolean bool) throws IOException {
        writer.write(0x1B);
        writer.write("E");
        writer.write((int) (bool ? 1 : 0));
        writer.flush();
    }

    /**
     * @throws IOException
     */
    public void setFontZoomIn() throws IOException {
        /* 横向纵向都放大一倍 */
        writer.write(0x1c);
        writer.write(0x21);
        writer.write(12);
        writer.write(0x1b);
        writer.write(0x21);
        writer.write(12);
        writer.flush();
    }

    /**
     * @throws IOException
     */
    public void setFontZoomInWidth() throws IOException {
        /* 横向放大一倍 */
        writer.write(0x1c);
        writer.write(0x21);
        writer.write(4);
        writer.write(0x1b);
        writer.write(0x21);
        writer.write(4);
        writer.flush();
    }

    /**
     * @throws IOException
     */
    public void setFontZoomInHeight() throws IOException {
        /* 纵向放大一倍 */
        writer.write(0x1c);
        writer.write(0x21);
        writer.write(8);
        writer.write(0x1b);
        writer.write(0x21);
        writer.write(8);
        writer.flush();
    }

    /**
     * @throws IOException
     */
    public void setFontDefault() throws IOException {

        writer.write(0x1c);
        writer.write(0x21);
        writer.write(1);
        writer.flush();
    }

    /**
     * Sets white on black printing
     */
    public void setInverse(Boolean bool) throws IOException {
        writer.write(0x1D);
        writer.write("B");
        writer.write((int) (bool ? 1 : 0));
        writer.flush();
    }

    /**
     * Sets underline and weight
     *
     * @param val 0 = no underline. 1 = single weight underline. 2 = double
     *            weight underline.
     */
    public void setUnderline(int val) throws IOException {
        writer.write(0x1B);
        writer.write("-");
        writer.write(val);
        writer.flush();
    }

    /**
     * Sets left, center, right justification
     *
     * @param val 0 = left justify. 1 = center justify. 2 = right justify.
     */
    public void setJustification(int val) throws IOException {
        writer.write(0x1B);
        writer.write("a");
        writer.write(val);
        writer.flush();
    }

    /**
     * @param str
     * @throws IOException
     */
    public void printStr(String str) throws IOException {
        writer.write(str);
        writer.flush();
    }

    /**
     * @param str
     * @throws IOException
     */
    public void printlnStr(String str) throws IOException {
        writer.write(str + "\n");
        writer.flush();
    }

    /**
     * print String value of obj
     *
     * @param obj
     * @throws IOException
     */
    public void printObj(Object obj) throws IOException {
        writer.write(obj.toString());
        writer.flush();
    }

    public void printlnObj(Object obj) throws IOException {
        writer.write(obj.toString() + "\n");
        writer.flush();
    }

    /**
     * print String value of element, and separator fill the gap between
     * elements
     *
     * @param lst
     * @param separator for example, use "\n" to break line
     * @throws IOException
     */
    public void printLst(List lst, String separator) throws IOException {
        for (Object o : lst) {
            this.printObj(o);
            this.printStr(separator);

        }
    }

    /**
     * print String value of element, and separator fill the gap between
     * elements
     *
     * @param map
     * @param kvSeparator
     * @param itemSeparator
     * @throws IOException
     */
    public void printMap(Map map, String kvSeparator, String itemSeparator)
            throws IOException {
        for (Object key : map.keySet()) {
            this.printObj(key);
            this.printStr(kvSeparator);
            this.printObj(map.get(key));
            this.printStr(itemSeparator);

        }

    }

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

        // ESC * m nL nH 点阵图
        byte[] escBmp = new byte[]{0x1B, 0x2A, 0x00, 0x00, 0x00};

        escBmp[2] = (byte) 0x21;

        //nL, nH
        escBmp[3] = (byte) (image.getWidth() % 256);
        escBmp[4] = (byte) (image.getWidth() / 256);

        for (int i = 0; i < image.getHeight(); i++) {
            write(escBmp);
            for (int j = 0; j < image.getWidth(); j++) {
                int off = i * image.getWidth() + j;
                if (pixels[off] == 0) {
                    data[ics] = 1;
                    //System.out.print(cs[ics]);
                } else {
                    //System.out.print("　");
                    data[ics] = 0;
                }
                ics = (ics + 1) % 3;

                if (j == image.getWidth() - 1) {
                    //System.out.println();
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

    /**
     * Encode and print QR code
     *
     * @param str        String to be encoded in QR.
     * @param errCorrect The degree of error correction. (48 <= n <= 51) 48 = level L /
     *                   7% recovery capacity. 49 = level M / 15% recovery capacity. 50
     *                   = level Q / 25% recovery capacity. 51 = level H / 30% recovery
     *                   capacity.
     * @param moduleSize The size of the QR module (pixel) in dots. The QR code will
     *                   not print if it is too big. Try setting this low and
     *                   experiment in making it larger.
     */
    public void printQR(String str, int errCorrect, int moduleSize)
            throws IOException {

        // save data function 80
        alignQr(1, moduleSize);
        writer.write(0x1D);// init
        writer.write("(k");// adjust height of barcode
        writer.write(str.length() + 3); // pl
        writer.write(0); // ph
        writer.write(49); // cn
        writer.write(80); // fn
        writer.write(48); //
        writer.write(str);

        // error correction function 69
        writer.write(0x1D);
        writer.write("(k");
        writer.write(3); // pl
        writer.write(0); // ph
        writer.write(49); // cn
        writer.write(69); // fn
        writer.write(errCorrect); // 48<= n <= 51

        // size function 67
        writer.write(0x1D);
        writer.write("(k");
        writer.write(3);
        writer.write(0);
        writer.write(49);
        writer.write(67);
        writer.write(moduleSize);// 1<= n <= 16

        // print function 81
        writer.write(0x1D);
        writer.write("(k");
        writer.write(3); // pl
        writer.write(0); // ph
        writer.write(49); // cn
        writer.write(81); // fn
        writer.write(48); // m

        writer.flush();
    }

    /**
     * Encode and print barcode
     *
     * @param code String to be encoded in the barcode. Different barcodes have
     *             different requirements on the length of data that can be
     *             encoded.
     * @param type Specify the type of barcode 65 = UPC-A. 66 = UPC-E. 67 =
     *             JAN13(EAN). 68 = JAN8(EAN). 69 = CODE39. 70 = ITF. 71 =
     *             CODABAR. 72 = CODE93. 73 = CODE128.
     * @param h    height of the barcode in points (1 <= n <= 255) @ param w
     *             width of module (2 <= n <=6). Barcode will not print if this
     *             value is too large. @param font Set font of HRI characters 0 =
     *             font A 1 = font B
     * @param pos  set position of HRI characters 0 = not printed. 1 = Above
     *             barcode. 2 = Below barcode. 3 = Both abo ve and below barcode.
     */
    public void printBarcode(String code, int type, int h, int w, int font,
                             int pos) throws IOException {

        // need to test for errors in length of code
        // also control for input type=0-6
        // GS H = HRI position
        writer.write(0x1D);
        writer.write("H");
        writer.write(pos); // 0=no print, 1=above, 2=below, 3=above & below

        // GS f = set barcode characters
        writer.write(0x1D);
        writer.write("f");
        writer.write(font);

        // GS h = sets barcode height
        writer.write(0x1D);
        writer.write("h");
        writer.write(h);

        // GS w = sets barcode width
        writer.write(0x1D);
        writer.write("w");
        writer.write(w);// module = 1-6

        // GS k
        writer.write(0x1D); // GS
        writer.write("k"); // k
        writer.write(type);// m = barcode type 0-6
        writer.write(code.length()); // length of encoded string
        writer.write(code);// d1-dk
        writer.write(0);// print barcode

        writer.flush();
    }

    /**
     * Encode and print PDF 417 barcode
     *
     * @param code String to be encoded in the barcode. Different barcodes have
     *             different requirements on the length of data that can be
     *             encoded.
     * @param type Specify the type of barcode 0 - Standard PDF417 1 - Standard
     *             PDF417
     * @param h    Height of the vertical module in dots 2 <= n <= 8. @ param w
     *             Height of the horizontal module in dots 1 <= n <= 4. @ param
     *             cols Number of columns 0 <= n <= 30. @ param rows Number of
     *             rows 0 (automatic), 3 <= n <= 90. @ param error set error
     *             correction level 48 <= n <= 56 (0 - 8).
     */
    public void printPSDCode(String code, int type, int h, int w, int cols,
                             int rows, int error) throws IOException {

        // print function 82
        writer.write(0x1D);
        writer.write("(k");
        writer.write(code.length()); // pl Code length
        writer.write(0); // ph
        writer.write(48); // cn
        writer.write(80); // fn
        writer.write(48); // m
        writer.write(code); // data to be encoded

        // function 65 specifies the number of columns
        writer.write(0x1D);// init
        writer.write("(k");// adjust height of barcode
        writer.write(3); // pl
        writer.write(0); // pH
        writer.write(48); // cn
        writer.write(65); // fn
        writer.write(cols);

        // function 66 number of rows
        writer.write(0x1D);// init
        writer.write("(k");// adjust height of barcode
        writer.write(3); // pl
        writer.write(0); // pH
        writer.write(48); // cn
        writer.write(66); // fn
        writer.write(rows); // num rows

        // module width function 67
        writer.write(0x1D);
        writer.write("(k");
        writer.write(3);// pL
        writer.write(0);// pH
        writer.write(48);// cn
        writer.write(67);// fn
        writer.write(w);// size of module 1<= n <= 4

        // module height fx 68
        writer.write(0x1D);
        writer.write("(k");
        writer.write(3);// pL
        writer.write(0);// pH
        writer.write(48);// cn
        writer.write(68);// fn
        writer.write(h);// size of module 2 <= n <= 8

        // error correction function 69
        writer.write(0x1D);
        writer.write("(k");
        writer.write(4);// pL
        writer.write(0);// pH
        writer.write(48);// cn
        writer.write(69);// fn
        writer.write(48);// m
        writer.write(error);// error correction

        // choose pdf417 type function 70
        writer.write(0x1D);
        writer.write("(k");
        writer.write(3);// pL
        writer.write(0);// pH
        writer.write(48);// cn
        writer.write(70);// fn
        writer.write(type);// set mode of pdf 0 or 1

        // print function 81
        writer.write(0x1D);
        writer.write("(k");
        writer.write(3); // pl
        writer.write(0); // ph
        writer.write(48); // cn
        writer.write(81); // fn
        writer.write(48); // m

        writer.flush();

    }

    /**
     * 二维码排版对齐方式
     *
     * @param position   0：居左(默认) 1：居中 2：居右
     * @param moduleSize 二维码version大小
     * @return
     * @throws IOException
     */
    public void alignQr(int position, int moduleSize) throws IOException {
        writer.write(0x1B);
        writer.write(97);
        if (position == 1) {
            writer.write(1);
            centerQr(moduleSize);
        } else if (position == 2) {
            writer.write(2);
            rightQr(moduleSize);
        } else {
            writer.write(0);
        }
    }

    /**
     * 居中牌排列
     *
     * @param moduleSize 二维码version大小
     * @throws IOException
     */
    public void centerQr(int moduleSize) throws IOException {
        switch (moduleSize) {
            case 1: {
                printSpace(16);
                break;
            }
            case 2: {
                printSpace(18);
                break;
            }
            case 3: {
                printSpace(20);
                break;
            }
            case 4: {
                printSpace(22);
                break;
            }
            case 5: {
                printSpace(24);
                break;
            }
            case 6: {
                printSpace(26);
                break;
            }
            default:
                break;
        }
    }

    /**
     * 二维码居右排列
     *
     * @param moduleSize 二维码version大小
     * @throws IOException
     */
    public void rightQr(int moduleSize) throws IOException {
        switch (moduleSize) {
            case 1:
                printSpace(14);
                break;
            case 2:
                printSpace(17);
                break;
            case 3:
                printSpace(20);
                break;
            case 4:
                printSpace(23);
                break;
            case 5:
                printSpace(26);
                break;
            case 6:
                printSpace(28);
                break;
            default:
                break;
        }
    }

    /**
     * 打印空白
     *
     * @param length 需要打印空白的长度
     * @throws IOException
     */
    public void printSpace(int length) throws IOException {
        for (int i = 0; i < length; i++) {
            writer.write(" ");
        }
        writer.flush();
    }

    /**
     * 初始化打印机
     *
     * @return
     * @throws IOException
     */
    public void init() throws IOException {
        writer.write(0x1B);
        writer.write(0x40);
    }

    /**
     * send command to open cashbox through printer
     *
     * @throws IOException
     */
    public void openCashbox() throws IOException {
        writer.write(27);
        writer.write(112);
        writer.write(0);
        writer.write(10);
        writer.write(10);

        writer.flush();
    }

    /**
     * To control the printer performing paper feed and cut paper finally
     *
     * @throws IOException
     */
    public void feedAndCut() throws IOException {
        feed();
        cut();

        writer.flush();

    }

    /**
     * To control the printer performing paper feed
     *
     * @throws IOException
     */
    public void feed() throws IOException {
        // 下面指令为打印完成后自动走纸
        writer.write(27);
        writer.write(100);
        writer.write(4);
        writer.write(10);

        writer.flush();

    }

    /**
     * Cut paper, functionality whether work depends on printer hardware
     *
     * @throws IOException
     */
    public void cut() throws IOException {
        // cut
        writer.write(0x1D);
        writer.write("V");
        writer.write(48);
        writer.write(0);

        writer.flush();

    }


    /**
     * at the end, close writer and socketOut
     */
    public void closeConn() {

        try {
            writer.close();
            socketOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    public static void main(String[] argus) {

        try {
            EscPosWebPrinter printer = new EscPosWebPrinter("10.128.38.244", 9100);

           /* for (int i = 1; i <= 1; i++) {
                printer.setJustification(1);
                //printer.printStr("deskNo");
               // printer.printQR("111", 48, 11);
                printer.printBarcode("12345678900",65,80,3,0,2);
                printer.image();
                printer.feed();
                printer.feedAndCut();
            }*/
            /*BufferedImage image = new BufferedImage(40, 40, BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D g2d = image.createGraphics();
            //设置颜色
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, 40, 40);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", 0, 15));
            g2d.drawString("你好", 5, 20);
            ImageIO.write(image, "png", new File("d:/doc/task.bmp"));*/
            // 打印图片
            BufferedImage image = ImageIO.read(new File("d:/doc/task.bmp"));

            byte[] bytes = ImagePixelUtils.imagePixelToPosByte_24(image, 33);

            BufferedOutputStream bufferOs = new BufferedOutputStream(printer.socketOut);
            printer.write(new byte[]{27, 51, 15});//设置行间距
            bufferOs.write(bytes);
            bufferOs.flush();

            //printer.printQR("111", 48, 11);
            printer.feedAndCut();
            printer.closeConn();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public OutputStream getSocketOut() {
        return socketOut;
    }
}