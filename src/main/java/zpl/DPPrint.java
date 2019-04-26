package zpl;

import com.google.common.collect.Lists;
import com.net.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Stack;

/*
 *@Author BieFeNg
 *@Date 2019/4/17 14:15
 *@DESC
 */
public class DPPrint {


    public static void main(String[] args) throws IOException {
        DPPrint print = new DPPrint();
        print.printImage();
        print.printFileToHoney();

    }

    public void test1() throws IOException {
        InputStream ins = new FileInputStream("d:/doc/testHex.txt");

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
        }
    }


    public void printFileToHoney() throws IOException {
        //InputStream ins = new FileInputStream("C:\\Log Files\\Intermec PC43d (203 dpi)\\新标签模板1.prn");
        InputStream ins = new FileInputStream("d:/doc/prtbuf.prn");
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("10.0.0.122", 9100));
        OutputStream os = socket.getOutputStream();

        BufferedOutputStream baos = new BufferedOutputStream(os);
        // baos.write("NASC 8\r\npp110,140\r\nFONTD \"MHeiGB18030C-Medium\",8,20,100\r\nPT \"雀巢咖啡1111111111111\r\nPF".getBytes());

        int len = 0;
        byte[] bytes = new byte[2048];
        while ((len = ins.read(bytes)) != -1) {
            baos.write(bytes, 0, len);
        }

        baos.flush();
        baos.close();
        socket.close();
    }


    public void printBarCode() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("10.0.0.122", 9100));
        OutputStream os = socket.getOutputStream();

        os.write("PRPOS 30,40\r\nBARSET \"CODE128\",2,1,3,120\r\nBARFONT \"Univers\",10,8,5,1,1 ON\r\nPRBAR \"7000001\"\r\nPF\r\n".getBytes());
    }

    public void printImage() throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedImage image = ImageIO.read(new File("E:\\workspace\\idea\\Net\\src\\main\\resources\\t11__1.bmp"));

        WritableRaster raster = image.getRaster();
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = raster.getPixels(0, 0, width, height, new int[width * height]);
        byte[] data = rllFormat(image);
        sb.append("PP30,100:").append("PRBUF " + (data.length + 6)).append("\r\n");

        FileOutputStream fos = new FileOutputStream("d:/doc/prtbuf.prn");
        fos.write(sb.toString().getBytes());
        fos.write(StringUtils.fromHexStrtoByteArr("40 02"));


        fos.write(StringUtils.fromHexStrtoByteArr(fromDecToHex(image.getWidth())));
        fos.write(StringUtils.fromHexStrtoByteArr(fromDecToHex(image.getHeight())));
/*

        List<Integer> list = Lists.asList(-3, new Integer[]{0, 3, 0, 3, 0, 3, 0, 3, -3});
        for (int i = 0; i < 4; i++) {
            for (Integer o : list) {
                fos.write(o.byteValue());
            }
        }
*/
        fos.write(data);
        /*for (Integer o : list1) {
            fos.write(o.byteValue());
        }*/
        fos.write("\r\nPF\r\nPRINT KEY OFF\r\n".getBytes());
        fos.flush();
        fos.close();
    }

    /**
     * 将16进制整形字符串转化为字节数组
     *
     * @param byteArrStr
     * @return
     */
    public static byte[] fromHexStrtoByteArr(String byteArrStr) {
        String[] strs = byteArrStr.trim().replaceAll("[\\r\\n\\t\\s]+", " ").split(" ");
        byte[] bytes = new byte[strs.length];
        int index = 0;
        for (String s : strs) {
            byte b = (byte) (Integer.parseInt(s, 16) & 0xFF);
            bytes[index++] = b;
        }
        return bytes;
    }

    public byte[] rllFormat(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableRaster raster = image.getRaster();
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = raster.getPixels(0, 0, width, height, new int[width * height]);

        for (int h = 0; h < height; h++) {
            int count = 0;
            boolean flag = false;  // 写入标记，当由白点变为黑点或黑点变为白点时，将统计到的连续的点数写入
            for (int w = 0; w < width; w++) {

                boolean changed = false;
                int offset = h * width + w;
                int pixel = pixels[offset];
                if (offset > 0) {
                    changed = pixel != pixels[offset - 1];
                } else {
                    changed = true;
                }
                if (w == 0) {
                    if (pixel != 0) {
                        count += 1;
                        continue;
                    } else {
                        flag = true;
                    }
                }

                if (changed || w == width - 1) {
                    flag = true;
                    if (w == width - 1) {
                        count += 1;
                    }
                } else if (!changed) {
                    count += 1;
                }
                if (flag) {
                    int div = count / 255;
                    int mol = count % 255;
                    for (int i = 0; i < div; i++) {
                        baos.write(255);
                        baos.write(0);
                    }
                    baos.write(mol);
                    if (w == width - 1 && pixel == 0) {
                        baos.write(0x00);
                    }
                    if (changed) {
                        count = 1;
                    } else {
                        count = 0;
                    }
                    flag = false;
                }
            }
        }
        return baos.toByteArray();
    }

    /**
     * 将10进制转为2个16进制值表示，并拼接为字符串,以空格隔开
     *
     * @param value
     */
    public static String fromDecToHex(int value) {
        /*int cardinality1 = 1 << 12;
        int cardinality2 = 1 << 8;
        int cardinality3 = 1 << 4;
        int cardinality4 = 1;*/
        StringBuffer sb = new StringBuffer();
        for (int i = 3; i >= 0; i--) {
            int cardinality = 1 << (4 * i);
            String hex = "0";
            if (i != 0) {
                int var = value / cardinality;
                hex = Integer.toHexString(var);
                value = value % cardinality;
            } else {
                hex = Integer.toHexString(value);

            }
            if (i == 1) {
                sb.append(" ");
            }
            sb.append(hex);

        }

       /* StringBuffer sb = new StringBuffer();
        sb.append(value / (cardinality1 - 1));
        value = value % (cardinality1 - 1);


        sb.append(value / (cardinality2 - 1));
        value = value % (cardinality2 - 1);

        sb.append(value / (cardinality3 - 1));
        value = value % (cardinality3 - 1);


        sb.append(value);*/

        return sb.toString();
    }


}
