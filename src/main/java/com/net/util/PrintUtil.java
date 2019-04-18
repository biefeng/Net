package com.net.util;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PrintQuality;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PrintUtil {
    public static void main(String[] args) throws Exception {
        PrintUtil printUtil = new PrintUtil();
        printUtil.print1("d:/doc/test.jpg");
    }


    public void print1(String fileName) {
        try {
            DocFlavor dof = null;
            // 根据用户选择不同的图片格式获得不同的打印设备
            if (fileName.endsWith(".gif")) {
                // gif
                dof = DocFlavor.INPUT_STREAM.GIF;
            } else if (fileName.endsWith(".jpg")) {
                // jpg
                dof = DocFlavor.INPUT_STREAM.JPEG;
            } else if (fileName.endsWith(".png")) {
                // png
                dof = DocFlavor.INPUT_STREAM.PNG;
            }
            // 字节流获取图片信息
            FileInputStream fin = new FileInputStream(fileName);
            // 获得打印属性
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

            // 每一次默认打印一页
            pras.add(new Copies(1));
            // 获得打印设备 ，字节流方式，图片格式
            PrintService pss[] = PrintServiceLookup.lookupPrintServices(dof,
                    pras);
            // 如果没有获取打印机
            if (pss.length == 0) {
                // 终止程序
                return;
            }
            PrintService ps;
            // 获取第一个打印机
            ps = pss[0];
            for (PrintService service : pss) {
                String name = service.getName();
                if (name.equals("Honeywell PC42t (203 dpi) - DP")) {
                    ps = service;
                }
            }
            System.out.println("Printing image..........." + ps);
            // 获得打印工作
            DocPrintJob job = ps.createPrintJob();


            // 设置打印内容
            Doc doc = new SimpleDoc(fin, dof, null);
            // 出现设置对话框
            PrintService service = ServiceUI.printDialog(null, 50, 50, pss, ps, dof, pras);


            PrintServiceAttributeSet attributes = service.getAttributes();
            // 开始打印
            job = service.createPrintJob();
            job.print(doc, pras);
            fin.close();


        } catch (IOException ie) {
            // 捕获io异常
            ie.printStackTrace();
        } catch (PrintException pe) {
            // 捕获打印异常
            pe.printStackTrace();
        }


    }

    public void print2() throws FileNotFoundException, PrintException {
        FileInputStream psStream = new FileInputStream("d:/doc/test.jpg");

        // 设置打印数据的格式，此处为图片JPEG、PDF格式
        DocFlavor psInFormat = DocFlavor.INPUT_STREAM.JPEG;
        // 创建打印数据
//	      DocAttributeSet docAttr = new HashDocAttributeSet();//设置文档属性
//	      Doc myDoc = new SimpleDoc(psStream, psInFormat, docAttr);
        Doc myDoc = new SimpleDoc(psStream, psInFormat, null);

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
}
