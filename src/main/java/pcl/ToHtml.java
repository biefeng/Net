package pcl;

//import gui.ava.html.image.generator.HtmlImageGenerator;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.usermodel.Picture;
import org.w3c.dom.Document;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToHtml {

    private static final Pattern MARKUP = Pattern.compile("<html>\\s+<head>(?<head>.*?)</head>(?<content>.*)</html>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    /**
     * Excel to HTML
     */
    public static String excel2Html(String filename, String htmlid, String ctxPath) throws Exception {
        InputStream input = new FileInputStream(filename);
        HSSFWorkbook excelBook = new HSSFWorkbook(input);

        excelBook.setSheetName(0, " ");

        ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        excelToHtmlConverter.setOutputColumnHeaders(false);
        excelToHtmlConverter.setOutputRowNumbers(false);

        excelToHtmlConverter.processWorkbook(excelBook);
        List pics = excelBook.getAllPictures();
        if (pics != null) {
            for (int i = 0; i < pics.size(); i++) {
                Picture pic = (Picture) pics.get(i);
                try {
                    pic.writeImageContent(new FileOutputStream(ctxPath + pic.suggestFullFileName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        Document htmlDocument = excelToHtmlConverter.getDocument();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
        return new String(outStream.toByteArray());
       /* OutputStream os = new FileOutputStream(new File(ctxPath, htmlid + ".html"));
        final PrintStream printStream = new PrintStream(os);
        printStream.print(new String(outStream.toByteArray()));
        printStream.close();*/
    }

    public static void t() throws Exception {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        String html = excel2Html("d:/test_1.xls", "hhhhhhhhhhhhhhhhhhhhhhh", "d:");
        Matcher matcher = MARKUP.matcher(html);
        if (matcher.find()) {
            String head = matcher.group("head");
            String content = matcher.group("content");
            html=head+content;
        }
        imageGenerator.loadHtml(html);
        BufferedImage image = imageGenerator.getBufferedImage();
        try {
            imageGenerator.saveAsImage("d:/doc/test/hello-world.BMP");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void test() throws Exception {
        String html = excel2Html("d:/test_1.xls", "hhhhhhhhhhhhhhhhhhhhhhh", "d:");
       /* Matcher matcher = MARKUP.matcher(html);
        if (matcher.find()) {
            String head = matcher.group("head");
            String content = matcher.group("content");
            html=head+content;
        }*/

        JLabel label = new JLabel(html);
        label.setSize(800, 900);

        BufferedImage image = new BufferedImage(
                label.getWidth(), label.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        {
            // paint the html to an image
            Graphics g = image.getGraphics();
            g.setColor(Color.BLACK);
            label.paint(g);
            g.dispose();
        }

        // get the byte array of the image (as jpeg)
        try {
            ImageIO.write(image, "png", new File("test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test1(){

    }

    public static void main(String[] args) {
        try {
            t();
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}