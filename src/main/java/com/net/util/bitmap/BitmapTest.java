package com.net.util.bitmap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BitmapTest {
    public static void main(String[] args) throws IOException {
       /* BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();
        g2d.setBackground(Color.blue);
        g2d.setFont(new Font("微软雅黑", 0, 30));
        g2d.drawString("你好", 0, 25);

        int[] p = image.getRGB(0, 0, image.getWidth(), image.getHeight(),
                new int[image.getWidth() * image.getHeight()], 0,
                image.getWidth());
        int[] pixels = image.getData().getPixels(0, 0, image.getWidth(), image.getHeight(), new int[image.getWidth() * image.getHeight()+80000]);
        ImageIO.write(image,"jpg",new File("d:/doc/bitmap.jpg"));*/
        BufferedImage image = ImageIO.read(new File("D:\\doc\\t11.png"));
        int[] pixels = image.getData().getPixels(0, 0, image.getWidth(), image.getHeight(), new int[image.getWidth() * image.getHeight() * 100]);
        char[] cs = {'你', '好', '呀'};
        int ics = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int off = i * image.getWidth() + j;
                if (pixels[off] == 0) {
                    System.out.print(cs[ics]);
                    ics = (ics + 1) % 3;
                } else {
                    System.out.print("　");
                }

                if (j == image.getWidth() - 1) {
                    System.out.println();
                }
            }
        }

    }
}
