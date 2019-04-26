package com.net.util;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

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
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
            b[i] = (byte) chars[i];
        }
        System.out.println(sbu.toString());
        return b;
    }

    public static String stringToAsciiHex(String value) {
        StringBuffer sb = new StringBuffer();
        for (String s : value.split("")) {
            sb.append(intStrToAscii(s, 16) + " ");
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

    }


}
