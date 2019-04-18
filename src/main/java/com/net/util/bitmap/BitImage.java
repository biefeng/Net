package com.net.util.bitmap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/*
 *@Author BieFeNg
 *@Date 2019/4/15 17:23
 *@DESC
 */
public class BitImage {

    public static void main(String[] args) {
        //print1();
    }

    public static void print() {
        try {
            BufferedImage image = ImageIO.read(new File("E:\\workspace\\idea\\Net\\src\\main\\resources\\1-test_3.bmp"));
            WritableRaster raster = image.getRaster();
            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = raster.getPixels(0, 0, width, height, new int[width * height]);
            System.out.println(pixels);
            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {

                    int off = h * image.getWidth() + w;
                    if (pixels[off] == 0) {
                        System.out.print("*");
                    } else {
                        System.out.print(" ");
                    }

                    if (w == image.getWidth() - 1) {
                        System.out.println();
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void print1(ByteArrayOutputStream baos) {
        try {
            BufferedImage image = ImageIO.read(new File("E:\\workspace\\idea\\Net\\src\\main\\resources\\1-test_3.bmp"));
            WritableRaster raster = image.getRaster();
            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = raster.getPixels(0, 0, width, height, new int[width * height]);
            System.out.println(pixels);
            int count = 0;
            StringBuilder sb = new StringBuilder();
            for (int i : pixels) {
                if (count == 0){}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
