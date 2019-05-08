package pcl;

import java.awt.image.BufferedImage;
import java.io.*;

import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

public class XHTMLToImage {

    private String inputFilename = "source.xhtml";
    private String outputFilename = "output.png";

    private int widthImage = 740;
    private int heightImage = 795;


    public void convertToImage() throws IOException {
        System.out.println("Calling convertToImage inputFilename=" + inputFilename + " outputFilename=" + outputFilename);
        final File f = new File(inputFilename);
        final Java2DRenderer renderer = new Java2DRenderer(f, widthImage, heightImage);
        final BufferedImage img = renderer.getImage();
        final FSImageWriter imageWriter = new FSImageWriter();
        imageWriter.setWriteCompressionQuality(10f);
        imageWriter.write(img, outputFilename);
        System.out.println("Done with rendering");

    }

    public static void htmlCovertTohtml(String sourceFilename,
                                        String targetFilename) {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
//		每行的最多字符，如果为0，不自动换行
        tidy.setWraplen(0);
//		是否保持属性中的空白字符
        tidy.setLiteralAttribs(true);
        // 需要转换的文件，当然你也可以转换URL的内容
        FileInputStream in;
        FileOutputStream out;
        try {
            in = new FileInputStream(sourceFilename);
            out = new FileOutputStream(targetFilename);
            // 输出的文件
            tidy.parse(in, out);
            // 转换完成关闭输入输出流
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) throws Exception {
        final XHTMLToImage renderer = new XHTMLToImage();
        htmlCovertTohtml("D:\\hhhhhhhhhhhhhhhhhhhhhhh.html","D:\\hhhhhhhhhhhhhhhhhhhhhhh.xhtml");
        if (args.length != 2) {
            renderer.inputFilename = "D:\\hhhhhhhhhhhhhhhhhhhhhhh.xhtml";
            renderer.outputFilename = "out.png";
            System.out.println("Usage : XHTMLToImage INPUTFILE.xhtml OUTPUTFILE.png <width> <height>");
        } else {
            renderer.inputFilename = args[0];
            renderer.outputFilename = args[1];
        }

        if (args.length == 4) {
            renderer.widthImage = 900;
            renderer.heightImage = 1000;
        }
        renderer.convertToImage();
    }

} 