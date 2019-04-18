package com.net.util;

import com.alibaba.fastjson.JSON;
import org.omg.CORBA.PRIVATE_MEMBER;
import sun.font.AttributeValues;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageUtil {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 700;

    private static final String DEFAULT_FONT_NAME = "微软雅黑";
    private static final int LINE_HEIGHT = 10;
    private static final int DEFAULT_LABEL_START_LOCATION = 40;
    private static final int DEFAULT_VALUE_START_LOCATION = 180;
    private static final int DEFAULT_LINT_START_LOCATION = 20;

    public static void main(String[] args) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("skuIndex", "1/1");
        data.put("dateStr", "2019-03-07 12:32:31");
        data.put("orderNo", "1100051500000006810");
        data.put("skuName", "牛肉面");
        data.put("isPackage", "外送");
        data.put("qty", String.valueOf(2));
        getTemplate(data);
    }

    public static void getTemplate(Map<String, String> data) throws IOException {
        //得到图片缓冲区
        BufferedImage bi = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);//INT精确度达到一定,RGB三原色，高度70,宽度150

        //得到它的绘制环境(这张图片的笔)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();


        g2.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);//填充一个矩形 左上角坐标(0,0),宽70,高150;填充整张图片
        //设置颜色
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);//填充整张图片(其实就是设置背景颜色)

        g2.setColor(Color.RED);
        g2.drawRect(0, 0, DEFAULT_WIDTH - 1, DEFAULT_HEIGHT - 1); //画边框

        int currY = 50;
        Font font = new Font(DEFAULT_FONT_NAME, Font.BOLD, 22);
        g2.setFont(font); //设置字体:字体、字号、大小
        g2.setColor(Color.BLACK);//设置背景颜色
        //桌号
        g2.drawString("桌号", DEFAULT_LABEL_START_LOCATION, currY); //向图片上写字符串
        //g2.setFont(new Font(DEFAULT_FONT_NAME,Font.ITALIC,50));
        currY += (getFontHeight(font) + LINE_HEIGHT);
        font = new Font(DEFAULT_FONT_NAME, 0, 50);
        drawDashLine(g2, font, DEFAULT_LINT_START_LOCATION, currY);
        currY += (LINE_HEIGHT + 10);
        font = new Font(DEFAULT_FONT_NAME, 0, 16);
        g2.setFont(font);
        g2.drawString("任务总数:", DEFAULT_LABEL_START_LOCATION, currY);
        g2.drawString(data.get("skuIndex"), DEFAULT_VALUE_START_LOCATION, currY);
        currY += (getFontHeight(font) + LINE_HEIGHT);
        g2.drawString("下单时间:", DEFAULT_LABEL_START_LOCATION, currY);
        g2.drawString(data.get("dateStr"), DEFAULT_VALUE_START_LOCATION, currY);
        currY += (getFontHeight(font) + LINE_HEIGHT);
        g2.drawString("单据号:", DEFAULT_LABEL_START_LOCATION, currY);
        g2.drawString(data.get("orderNo"), DEFAULT_VALUE_START_LOCATION, currY);
        currY += (getFontHeight(font) + LINE_HEIGHT);
        font = new Font(DEFAULT_FONT_NAME, 0, 50);
        drawDashLine(g2, font, DEFAULT_LINT_START_LOCATION, currY);
        currY += (LINE_HEIGHT + 10);
        font = new Font(DEFAULT_FONT_NAME, 0, 18);
        g2.setFont(font);
        g2.drawString("品名: ", DEFAULT_LABEL_START_LOCATION, currY);
        g2.drawString("数量: ", 320, currY);
        currY += (getFontHeight(font) + LINE_HEIGHT);
        g2.drawString(data.get("skuName") + "(" + data.get("isPackage") + ")", DEFAULT_LABEL_START_LOCATION, currY);
        g2.drawString(data.get("qty"), 320, currY);
        currY += (getFontHeight(font) + LINE_HEIGHT);
        font = new Font(DEFAULT_FONT_NAME, 0, 50);
        drawDashLine(g2, font, DEFAULT_LINT_START_LOCATION, currY);
        currY += (LINE_HEIGHT + 10);
        font = new Font(DEFAULT_FONT_NAME, Font.BOLD, 20);
        g2.setFont(font);
        g2.drawString("备注: ", DEFAULT_LABEL_START_LOCATION, currY);
        String text = JSON.toJSONString(data);
        BufferedImage image=null;
        try {
            image = QRCodeUtil.createImage(text, null, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*RenderingHints hints = new RenderingHints();
        BufferedImageOp op = new ColorConvertOp(null);*/
        g2.drawImage(image,null,180,currY);
        ImageIO.write(bi, "JPEG", new FileOutputStream("d:/doc/a.jpg"));//保存图片 JPEG表示保存格式
    }

    private static void drawDashLine(Graphics2D g2, Font font, int x, int y) {
        g2.setFont(font);
        g2.drawString("-------------------------", x, y);
    }

    private static Object getFontWidth(Font font, String str) {

        FontMetrics fm = new JLabel().getFontMetrics(font);

        if (null == str && str.length() == 0) {
            return 0;
        }
        return fm.stringWidth(str);
    }

    private static int getFontHeight(Font font) {
        FontMetrics fontMetrics = new JLabel().getFontMetrics(font);
        return fontMetrics.getHeight();
    }


}
