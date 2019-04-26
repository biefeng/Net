package pcl;

import com.net.util.StringUtils;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/*
 *@Author BieFeNg
 *@Date 2019/4/10 10:25
 *@DESC
 */
public class PclPrint {

    private static final int DEFAULT_BYTE_LEN = 8;

    private String uelS = "1B 25 2D  31 32 33 34 35 58"; //  ESC%-12345X
    private String lfS = "0A";  // LF
    private String lf_cr_str = " \r\n";  //LFCR
    private String prj_str = "@PJL "; // @PJL
    private String enter_cmd = "@PJL ENTER LANGUAGE = PCL";
    private String comment_str = "@PJL COMMENT Beginning PCL Job";
    private String reset_cmd = "1B 45";  // ESCE
    private static String b4w = "1B 2A 62 34 57";

    private byte[] bytes1 = StringUtils.fromHexStrtoByteArr(b4w);
    private byte esc_b = 27;
    private byte[] uel_b = fromHexStrtoByteArr(uelS);
    private byte[] lf_b = fromHexStrtoByteArr(lfS);
    private byte[] lf_cr_b = lf_cr_str.getBytes();
    private byte[] reset_cmd_b = fromHexStrtoByteArr(reset_cmd);
    //private static byte[] b4w_b = fromHexStrtoByteArr(b4w);
    //private static byte[] b4w_b = getRasterBlockCode(DEFAULT_NUM);

    public static void main(String[] args) throws IOException {
        PclPrint pclPrint = new PclPrint();
        pclPrint.genePclFile();
    }



    public void doPrint() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("10.128.7.190", 9100));
        try (OutputStream os = socket.getOutputStream(); BufferedOutputStream bos = new BufferedOutputStream(os);) {
            InputStream ins = new FileInputStream("d:/doc/hhhhhhhhhhhhhhhhhhhhhhhh.pcl");
            int len = 0;
            byte buff[] = new byte[2048];
            while ((len = ins.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
            bos.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void genePclFile() {
        PclPrint pclPrint = new PclPrint();
        try {
            //InputStream in = new FileInputStream("E:\\workspace\\idea\\Net\\src\\main\\resources\\1-test_3.bmp");
            InputStream in = new FileInputStream("d:/doc/test_1.png");
            InputStream prefix = new FileInputStream("d:/test_2.pcl");
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("d:/doc/hhhhhhhhhhhhhhhhhhhhhhhh.pcl"));
            int len = 0;
            byte[] buffer = new byte[2048];
            while ((len = prefix.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            handImage(in, bos);
            pclPrint.end(bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据设置的值获取图片压缩指令
     *
     * @return
     */
    public static byte[] getRasterBlockCode(int size) {
        String s = stringToAsciiHex(String.valueOf(size));
        String result = "1B 2A 62 " + s + " 57";
        return fromHexStrtoByteArr(result);
    }

    /**
     * 可手动设置初始设置
     */
    private void init(OutputStream bos) throws IOException {
        bos.write(uel_b);
        bos.write(prj_str.getBytes());
        bos.write(lf_cr_b);
        bos.write(comment_str.getBytes());
        bos.write(lf_cr_b);
        bos.write(enter_cmd.getBytes());
        bos.write(lf_cr_b);
        bos.write(reset_cmd_b);
    }

    /**
     * 读取24位图，完成打印
     *
     * @param os
     * @param ins
     * @throws IOException
     */
    private void printJob(OutputStream os, InputStream ins) throws IOException {
        BufferedImage image = ImageIO.read(ins);
        BufferedImage bit = ImagePixelUtil._24to1bit(image);
        int width = bit.getWidth();
        int height = bit.getHeight();
        int colNum = width / 4;
        int colNumMol = width % 4;

        int rowNum = height / 4;
        int rowNumMol = height % 4;


        os.write("````````PCL PRINT JOB``````````".getBytes());
    }

    private void end(OutputStream os) throws IOException {
        os.write(reset_cmd_b);
        os.write(uel_b);
        os.flush();
        os.close();
    }

    /**
     * 将单色位图处理成可打印的数据
     */
    public static void handImage(InputStream in, OutputStream baos) {
        try {
            BufferedImage image = ImageIO.read(in);
            WritableRaster raster = image.getRaster();
            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = raster.getPixels(0, 0, width, height, new int[width * height]);

            int count_8 = 0;
            int byte_size = width / DEFAULT_BYTE_LEN;
            int mol = width % DEFAULT_BYTE_LEN;
            int rowSize = width;
            if (mol != 0) {
                rowSize = width + DEFAULT_BYTE_LEN;
            }
            byte[] b4w_ = getRasterBlockCode(mol == 0 ? byte_size : byte_size + 1);
            int count_w = 0;
            StringBuilder strBuild = new StringBuilder();
            StringBuilder sb = new StringBuilder();
            outer:
            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    int off = h * width + w;
                    if (count_w == 0) {

                        baos.write(b4w_);
                        strBuild.append("b4w");
                    }
                    if (pixels[off] == 0) {
                        sb.append("1");
                    } else {
                        sb.append("0");
                    }

                    if (count_8++ == DEFAULT_BYTE_LEN - 1) {
                        int val = Integer.parseInt(sb.toString(), 2);
                        baos.write(val);
                        sb = new StringBuilder();
                        count_8 = 0;
                        strBuild.append(val + " ");
                    }
                    if (count_w++ == rowSize - 1 && mol == 0) {
                        count_w = 0;
                        //strBuild.append("\r\n");
                        continue outer;
                    }
                }
                while (count_w++ < rowSize - 2) {
                    sb.append("0");
                }
                count_8 = 0;
                count_w = 0;
                int val = Integer.parseInt(sb.toString(), 2);
                sb = new StringBuilder();
                //strBuild.append(val+" "+"\r\n");
                baos.write(val);
            }
            System.out.println(strBuild.toString());
           /* if (null != data && data.length != 0) {
                int index = 7;
                int res = 0;
                for (byte b : data) {

                    if (index >= 0) {
                        if (b != 0) {
                            b = 1;
                            res += (b << index--);
                        }
                    } else {
                        res = 0;
                        index = 7;

                    }
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void geneCode(InputStream ins) {
        try (FileOutputStream fos = new FileOutputStream("d:/doc/hhhhh.pcl")) {
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            init(bos);
            printJob(bos, ins);
            end(bos);
            bos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        try (FileInputStream fis = new FileInputStream("E:\\workspace\\idea\\pcl-viewer\\test\\owl.pcl")) {
            Socket socket = new Socket("10.128.7.190", 9100);
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff = new byte[2048];
            int len = 0;
            while ((len = fis.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            byte[] bytes = baos.toByteArray();
            System.out.println(bytes.length);
            outputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * 将10进制整形字符串转化为字节数组
     *
     * @param byteArrStr
     * @return
     */
    public static byte[] fromDecStrtoByteArr(String byteArrStr) {
        String[] strs = byteArrStr.trim().replaceAll("[\\r\\n\\t\\s]+", " ").split(" ");
        byte[] bytes = new byte[strs.length];
        int index = 0;
        for (String s : strs) {
            byte b = (byte) (Integer.parseInt(s, 10) & 0xFF);
            bytes[index++] = b;
        }
        return bytes;
    }

    public static String stringToAscii(String value, int radix) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        String s = sbu.toString();
        int val = Integer.parseInt(s);
        if (radix == 16) {
            return Integer.toHexString(val);
        } else if (radix == 2) {
            return Integer.toBinaryString(val);
        } else {
            return s;
        }
    }

    /**
     * 将十进制整数值值转化为ASCII（16进制）
     *
     * @param value
     * @return
     */
    public static String stringToAsciiHex(String value) {
        StringBuffer sb = new StringBuffer();
        for (String s : value.split("")) {
            sb.append(stringToAscii(s, 16) + " ");
        }
        return sb.toString();

    }


    public void geneTemplate(){
        String uelS = "1B 25 2D  31 32 33 34 35 58";
        String lfS = "0A";
        String lf_cr_str = " \r\n";
        String prj_str = "@PJL ";
        String enter_cmd = "@PJL ENTER LANGUAGE = PCL";
        String comment_str = "@PJL COMMENT Beginning PostScript Job";
        String reset_cmd = "1B 45";

        byte esc_b = 27;
        byte[] uel_b = fromHexStrtoByteArr(uelS);
        byte[] lf_b = fromHexStrtoByteArr(lfS);
        byte[] lf_cr_b = lf_cr_str.getBytes();
        byte[] reset_cmd_b = fromHexStrtoByteArr(reset_cmd);
        try (FileOutputStream fos = new FileOutputStream("d:/doc/hhhhh.pcl")) {
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(uel_b);
            bos.write(prj_str.getBytes());
            bos.write(lf_cr_b);
            bos.write(comment_str.getBytes());
            bos.write(lf_cr_b);
            bos.write(enter_cmd.getBytes());
            bos.write(lf_cr_b);
            bos.write(esc_b);
            bos.write("E".getBytes());
            bos.write("````````PCL PRINT JOB``````````".getBytes());
            bos.write(esc_b);
            bos.write("E".getBytes());
            bos.write(uel_b);
            bos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
