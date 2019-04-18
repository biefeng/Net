package com.net.util;/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.alibaba.fastjson.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.*;
import java.awt.*;
import java.net.*;
import java.awt.image.*;
import java.awt.print.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.swing.*;

public class Print2DGraphics implements Printable {

    /*-----QR Constants----*/

    private static final String CHARSET = "utf-8";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 15;


    /*--------Task Ticket Constants----------*/
    /*票宽*/
    private static final int DEFAULT_WIDTH = 60 ;
    /*票高*/
    private static final int DEFAULT_HEIGHT = 80 ;
    /*默认字体*/
    private static final String DEFAULT_FONT_NAME = "微软雅黑";
    /*行间距*/
    private static final int LINE_HEIGHT = 1;
    /*标签名开始X轴值*/
    private static final int DEFAULT_LABEL_START_LOCATION = 4 ;
    /*值开始X轴值*/
    private static final int DEFAULT_VALUE_START_LOCATION = 18 ;
    /*虚线开始X轴值*/
    private static final int DEFAULT_LINT_START_LOCATION = 2 ;

    private static final int FONT_SIZE_1 = 2;
    private static final int FONT_SIZE_2 = 3;
    private static final int FONT_SIZE_3 = 4;

    private Map<String,String> data =new HashMap<>();
    {
        data.put("skuIndex", "1/1");
        data.put("dateStr", "2019-03-07 12:32:31");
        data.put("orderNo", "1100051500000006810");
        data.put("skuName", "牛肉面");
        data.put("isPackage", "外送");
        data.put("qty", String.valueOf(2));
    }

    public Print2DGraphics() {

        /* Construct the print request specification.
         * The print data is a Printable object.
         * the request additonally specifies a job name, 2 copies, and
         * landscape orientation of the media.
         */
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        //aset.add(OrientationRequested.LANDSCAPE);
        //aset.add(new Copies(1));
        //aset.add(new JobName("My job", null));
        //aset.add(MediaSize.Engineering.E);



        /* locate a print service that can handle the request */
        PrintService[] services =
                PrintServiceLookup.lookupPrintServices(flavor, aset);

        if (services.length > 0) {

            for (PrintService printService:services){
                String name = printService.getName();
                if (name.equals("Honeywell PC42t (203 dpi) - DP")){
                    System.out.println("selected printer " + services[0].getName());

                    /* create a print job for the chosen service */
                    DocPrintJob pj =printService.createPrintJob();

                    try {
                        // 通俗理解就是书、文档
                        Book book = new Book();
                        // 设置成竖打
                        PageFormat pf = new PageFormat();
                        pf.setOrientation(PageFormat.PORTRAIT);
                        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
                        Paper p = new Paper();
                        p.setSize(142, 85);// 纸张大小
                        p.setImageableArea(10, 10, 142, 70);
                        pf.setPaper(p);
                        book.append(this,pf);
                        /*PrinterJob job = PrinterJob.getPrinterJob();
                        job.print();*/
                        /*
                         * Create a Doc object to hold the print data.
                         */
                        Doc doc = new SimpleDoc(this, flavor, null);

                        /* print the doc as specified */
                        pj.print(doc, aset);

                        /*
                         * Do not explicitly call System.exit() when print returns.
                         * Printing can be asynchronous so may be executing in a
                         * separate thread.
                         * If you want to explicitly exit the VM, use a print job
                         * listener to be notified when it is safe to do so.
                         */

                    } catch (PrintException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public int print(Graphics g,PageFormat pf,int pageIndex) {

        if (pageIndex == 0) {
            Graphics2D g2= (Graphics2D)g;
            g2.translate(pf.getImageableX(),pf.getImageableY());
            g2.setColor(Color.black);
            g2.drawString("example string", 0, 0);
            //g2.fillRect(0, 0, 200, 200);
           g2.translate(pf.getImageableX(), pf.getImageableY());

           /* g2.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);//填充一个矩形 左上角坐标(0,0),宽70,高150;填充整张图片
            //设置颜色
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);

            g2.setColor(Color.RED);
            g2.drawRect(0, 0, DEFAULT_WIDTH - 1, DEFAULT_HEIGHT - 1); //画边框

            int currY = 5;
            g2.setColor(Color.BLACK);
            Font font = new Font(DEFAULT_FONT_NAME, Font.BOLD, FONT_SIZE_3);
            g2.setFont(font);

            currY = drawString(g2, "桌号", FONT_SIZE_3, DEFAULT_LABEL_START_LOCATION, currY);

            currY = drawDashLine(g2, 20, DEFAULT_LINT_START_LOCATION, currY);

            drawString(g2, "任务总数:", FONT_SIZE_1
                    , DEFAULT_LABEL_START_LOCATION, currY);
            currY = drawString(g2, data.get("skuIndex"), FONT_SIZE_1
                    , DEFAULT_VALUE_START_LOCATION, currY);

            drawString(g2, "下单时间:", FONT_SIZE_1
                    , DEFAULT_LABEL_START_LOCATION, currY);
            currY = drawString(g2, data.get("dateStr"), FONT_SIZE_1
                    , DEFAULT_VALUE_START_LOCATION, currY);

            drawString(g2, "单据号:", FONT_SIZE_1
                    , DEFAULT_LABEL_START_LOCATION, currY);
            currY = drawString(g2, data.get("orderNo"), FONT_SIZE_1
                    , DEFAULT_VALUE_START_LOCATION, currY);

            currY = drawDashLine(g2, 20, DEFAULT_LINT_START_LOCATION, currY);

            drawString(g2, "品名: ", FONT_SIZE_1, Font.BOLD, DEFAULT_LABEL_START_LOCATION, currY);
            currY = drawString(g2, "数量: ", FONT_SIZE_1, Font.BOLD, 18, currY);

            drawString(g2, data.get("skuName") + "(" + data.get("isPackage") + ")", FONT_SIZE_1, DEFAULT_LABEL_START_LOCATION, currY);
            currY = drawString(g2, data.get("qty"), FONT_SIZE_1, 18, currY);

            currY = drawDashLine(g2, 20, DEFAULT_LINT_START_LOCATION, currY);


            drawString(g2, "备注: ", FONT_SIZE_3, Font.BOLD, DEFAULT_LABEL_START_LOCATION, currY);
            String text = JSON.toJSONString(data);
            text="{'id':2859,'taskNo':'CTN100051903070003','diningNo':'214'}";
            BufferedImage image = null;
            try {
                image = getQRCode(text);
            } catch (Exception e) {
                e.printStackTrace();
            }

            g2.drawImage(image, null, 10, currY);*/

            return Printable.PAGE_EXISTS;
        } else {
            return Printable.NO_SUCH_PAGE;
        }
    }

    public BufferedImage getQRCode(String content) throws WriterException {
        Hashtable<EncodeHintType, Object> encodeHints = new Hashtable<EncodeHintType, Object>();
        encodeHints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        encodeHints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        encodeHints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, encodeHints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        return image;
    }

    public int drawString(Graphics2D g2, String str, int fontSize, int fontStyle, int x, int y) {
        Font font = new Font(DEFAULT_FONT_NAME, fontStyle, fontSize);
        g2.setFont(font);
        g2.drawString(str, x, y);
        y += (getFontHeight(font) + LINE_HEIGHT);
        return y;
    }

    public int drawString(Graphics2D g2, String str, int fontSize, int x, int y) {
        //默认不加粗
        return drawString(g2, str, fontSize, 0, x, y);
    }


    public int drawDashLine(Graphics2D g2, int fontSize, int x, int y) {
        Font font = new Font(DEFAULT_FONT_NAME, 0, fontSize);
        g2.setFont(font);
        g2.drawString("-------------------------", x, y);
        return y + LINE_HEIGHT + 1;
    }

    public int getFontHeight(Font font) {
        FontMetrics fontMetrics = new JLabel().getFontMetrics(font);
        return fontMetrics.getHeight();
    }

    public static void main(String arg[]) {
        Print2DGraphics sp = new Print2DGraphics();
    }
}