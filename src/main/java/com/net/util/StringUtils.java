package com.net.util;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StringUtils {

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

    public static String intStrToAscii(String value, int radix) {
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

    public static byte[] stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        byte[] b = new byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            /*if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }*/
            b[i]= (byte) chars[i];
        }
        return b;
    }

    public static String stringToAsciiHex(String value) {
       StringBuffer sb = new StringBuffer();
        for (String s:value.split("")){
           sb.append(intStrToAscii(value,16)+" ");
       }
        return sb.toString();

    }

    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.trim().replaceAll("[\\r\\n\\t\\s]+", " ").split(" ");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i], 16));
        }
        return sbu.toString();
    }

    public static void main(String[] args) {
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
