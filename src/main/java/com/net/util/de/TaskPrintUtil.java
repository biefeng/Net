package com.net.util.de;

import com.alibaba.fastjson.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.net.util.QRCodeUtil;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class TaskPrintUtil {

    /*-----QR Constants----*/

    private static final String CHARSET = "utf-8";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 250;


    /*--------Task Ticket Constants----------*/
    /*票宽*/
    private static final int DEFAULT_WIDTH = 600;
    /*票高*/
    private static final int DEFAULT_HEIGHT = 800;
    /*默认字体*/
    private static final String DEFAULT_FONT_NAME = "微软雅黑";
    /*行间距*/
    private static final int LINE_HEIGHT = 10;
    /*标签名开始X轴值*/
    private static final int DEFAULT_LABEL_START_LOCATION = 40;
    /*值开始X轴值*/
    private static final int DEFAULT_VALUE_START_LOCATION = 180;
    /*虚线开始X轴值*/
    private static final int DEFAULT_LINT_START_LOCATION = 20;

    private static final int FONT_SIZE_1 = 16;
    private static final int FONT_SIZE_2 = 18;
    private static final int FONT_SIZE_3 = 20;

    public void printTask(InputStream image) throws PrintException {
        //FileInputStream psStream = new FileInputStream("d:/doc/test.jpg");

        // 设置打印数据的格式，此处为图片JPEG、PDF格式
        DocFlavor psInFormat = DocFlavor.INPUT_STREAM.JPEG;
        // 创建打印数据
//	      DocAttributeSet docAttr = new HashDocAttributeSet();//设置文档属性
//	      Doc myDoc = new SimpleDoc(psStream, psInFormat, docAttr);
        DocAttributeSet das = new HashDocAttributeSet();
        das.add(new PrinterResolution(203, 203, PrinterResolution.DPI));
        //das.add(new PrintQuality(3));
        Doc myDoc = new SimpleDoc(image, DocFlavor.INPUT_STREAM.JPEG, das);


        // 设置打印属性
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
//		aset.add(new Copies(3));// 打印份数，3份

        // 查找所有打印服务
        PrintService[] services = PrintServiceLookup.lookupPrintServices(psInFormat, aset);

        // this step is necessary because I have several printers configured
        // 将所有查找出来的打印机与自己想要的打印机进行匹配，找出自己想要的打印机
        PrintService myPrinter = null;
        for (int i = 0; i < services.length; i++) {
            String svcName = services[i].toString();
            if (svcName.contains("EPSON TM-m30 Receipt")) {
                myPrinter = services[i];
                break;
            }
        }
        if (myPrinter == null) {
            myPrinter = services[0];
        }

        DocPrintJob job = myPrinter.createPrintJob();// 创建文档打印作业
        job.print(myDoc, aset);// 打印文档
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

    public InputStream getTemplate(Map<String, String> data) throws IOException {
        //得到图片缓冲区
        BufferedImage bi = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_BYTE_BINARY);//INT精确度达到一定,RGB三原色，高度70,宽度150

        Graphics2D g2 = (Graphics2D) bi.getGraphics();

        g2.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);//填充一个矩形 左上角坐标(0,0),宽70,高150;填充整张图片
        //设置颜色
        g2.setColor(Color.white);
        g2.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        g2.setColor(Color.RED);
        g2.drawRect(0, 0, DEFAULT_WIDTH - 1, DEFAULT_HEIGHT - 1); //画边框

        int currY = 50;
        g2.setColor(Color.BLACK);
        Font font = new Font(DEFAULT_FONT_NAME, Font.BOLD, FONT_SIZE_3);
        g2.setFont(font);
        int tmpY = drawString(g2, "桌号", FONT_SIZE_3, DEFAULT_LABEL_START_LOCATION, currY);
        currY+=LINE_HEIGHT;
        //currY = drawDashLine(g2, 50, DEFAULT_LINT_START_LOCATION, currY);
        g2.setStroke(new BasicStroke(1.5f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,2,new float[]{15,10},0));
        g2.drawLine(30,currY,570,currY);
        currY=tmpY;
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
        currY = drawString(g2, "数量: ", FONT_SIZE_1, Font.BOLD, 180, currY);

        drawString(g2, data.get("skuName") + "(" + data.get("isPackage") + ")", FONT_SIZE_1, DEFAULT_LABEL_START_LOCATION, currY);
        currY = drawString(g2, data.get("qty"), FONT_SIZE_1, 18, currY);

        currY = drawDashLine(g2, 20, DEFAULT_LINT_START_LOCATION, currY);


        drawString(g2, "备注: ", FONT_SIZE_3, Font.BOLD, DEFAULT_LABEL_START_LOCATION, currY);
        String text = JSON.toJSONString(data);
        text = "{'id':2859,'taskNo':'CTN100051903070003','diningNo':'214'}";
        BufferedImage image = null;
        try {
            image = getQRCode(text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        g2.drawImage(image, null, 180, currY);
        ImageIO.write(bi, "PNG", new FileOutputStream("d:/doc/task.bmp"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bi, "PNG", outputStream);
        byte[] bytes = outputStream.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return inputStream;
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

    public static void main(String[] args) throws IOException, PrintException {
        TaskPrintUtil taskPrintUtil = new TaskPrintUtil();
        Map<String, String> data = new HashMap<>();
        data.put("skuIndex", "1/1");
        data.put("dateStr", "2019-03-07 12:32:31");
        data.put("orderNo", "1100051500000006810");
        data.put("skuName", "牛肉面");
        data.put("isPackage", "外送");
        data.put("qty", String.valueOf(2));
        InputStream image = taskPrintUtil.getTemplate(data);
        //taskPrintUtil.printTask(image);
    }
}
