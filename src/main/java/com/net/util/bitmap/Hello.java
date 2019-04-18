package com.net.util.bitmap;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Hello
{
 
    public static void main(String[] args)
    {
        BufferedImage image = new BufferedImage(200, 200,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setFont(new Font("华文彩云", Font.BOLD, 60));
        g.setColor(new Color(0xfeab0899));
        g.drawString("我爱你", 2, image.getHeight() - 20);
        int[] p = image.getRGB(0, 0, image.getWidth(), image.getHeight(),
                new int[image.getWidth() * image.getHeight()], 0,
                image.getWidth());
        char[] cs = {'我', '爱', '你'};
        int ics = 0;
        for (int i = 0; i < image.getHeight(); i++)
        {
            for (int j = 0; j < image.getWidth(); j++)
            {
                int off = i * image.getWidth() + j;
                if (p[off] != 0)
                {
                    System.out.print(cs[ics]);
                    ics = (ics+1) % 3;
                } else
                {
                    System.out.print("　");
                }
 
                if (j == image.getWidth() -1 )
                {
                    System.out.println();
                }
 
            }
        }
    }
}