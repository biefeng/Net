package zpl;

import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.ZebraPrintException;
import fr.w3blog.zpl.model.ZebraUtils;
import fr.w3blog.zpl.model.element.ZebraBarCode39;
import fr.w3blog.zpl.model.element.ZebraText;

import javax.print.DocFlavor;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import java.awt.print.PrinterJob;
import java.io.*;
import java.net.Socket;

/*
 *@Author BieFeNg
 *@Date 2019/4/16 17:03
 *@DESC
 */
public class ZplPrint {


    public static void main(String[] args) throws ZebraPrintException, IOException {
        ZebraLabel zebraLabel = new ZebraLabel(912, 912);
        zebraLabel.setDefaultZebraFont(ZebraFont.ZEBRA_ZERO);

        zebraLabel.addElement(new ZebraText(10, 84, "Product:", 14));
        zebraLabel.addElement(new ZebraText(395, 85, "Camera", 14));

        zebraLabel.addElement(new ZebraText(10, 161, "CA201212AA", 14));

        //Add Code Bar 39
        zebraLabel.addElement(new ZebraBarCode39(10, 297, "CA201212AA", 118, 2, 2));

        zebraLabel.addElement(new ZebraText(10, 365, "Qté:", 11));
        zebraLabel.addElement(new ZebraText(180, 365, "3", 11));
        zebraLabel.addElement(new ZebraText(317, 365, "QA", 11));

        zebraLabel.addElement(new ZebraText(10, 520, "Ref log:", 11));


        zebraLabel.addElement(new ZebraText(180, 520, "0035", 11));
        zebraLabel.addElement(new ZebraText(10, 596, "Ref client:", 11));
        zebraLabel.addElement(new ZebraText(180, 599, "1234", 11));

        zebraLabel.getZplCode();

        //ZebraUtils.printZpl(zebraLabel, "10.0.0.70", 9100);
        //test();
    }


    public static void test() throws IOException {
        Socket socket = new Socket("10.0.0.70", 9100);

        try (OutputStream outputStream = socket.getOutputStream(); OutputStreamWriter writer = new OutputStreamWriter(outputStream, "GBK")) {
            writer.write(
                    "PRPOS 130,50\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"XXXXXXXXXXXX\"\n" +
                            "\n" +
                            "PRPOS 20,90\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"生产厂：\"\n" +
                            "\n" +
                            "PRPOS 130,90\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"XXXXXXXX\"\n" +
                            "\n" +
                            "PRPOS 20,130\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"品牌：\"\n" +
                            "\n" +
                            "PRPOS 130,130\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"XXXXXXXX\"\n" +
                            "\n" +
                            "PRPOS 20,170\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"采购数量：\"\n" +
                            "\n" +
                            "PRPOS 150,170\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"XXX\"\n" +
                            "\n" +
                            "PRPOS 280,170\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"发货数量：\"\n" +
                            "\n" +
                            "PRPOS 410,170\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"XXX\"\n" +
                            "\n" +
                            "PRPOS 550,170\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"计量单位：\"\n" +
                            "\n" +
                            "PRPOS 680,170\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"XXX\"\n" +
                            "\n" +
                            "PRPOS 220,210\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"订单号：\"\n" +
                            "\n" +
                            "PRPOS 360,210\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"XXXXXX\"\n" +
                            "\n" +
                            "PRPOS 220,250\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"规格型号：\"\n" +
                            "\n" +
                            "PRPOS 360,250\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"XXXXXX\"\n" +
                            "\n" +
                            "PRPOS 220,290\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"物资名称：\"\n" +
                            "\n" +
                            "PRPOS 360,290\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"XXXXXX\"\n" +
                            "\n" +
                            "PRPOS 220,330\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"物资编码：\"\n" +
                            "\n" +
                            "PRPOS 360,330\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"XXXXXX\"\n" +
                            "\n" +
                            "\n" +
                            "PRPOS 220,370\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"收货单位：\"\n" +
                            "\n" +
                            "PRPOS 360,370\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"XXXXXX\"\n" +
                            "\n" +
                            "PRPOS 220,410\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"供货商名：\"\n" +
                            "\n" +
                            "PRPOS 360,410\n" +
                            "FONTD \"FZShuSong-Z01S\",10\n" +
                            "PT \"XXXXXX\"\n" +
                            "\n" +
                            "PRPOS 20,90\n" +
                            "DIR 1\n" +
                            "PRLINE 760,3\n" +
                            "\n" +
                            "PRPOS 20,130\n" +
                            "DIR 1\n" +
                            "PRLINE 760,3\n" +
                            "\n" +
                            "PRPOS 20,170\n" +
                            "DIR 1\n" +
                            "PRLINE 760,3\n" +
                            "\n" +
                            "PRPOS 20,210\n" +
                            "DIR 1\n" +
                            "PRLINE 760,3\n" +
                            "\n" +
                            "PRPOS 220,250\n" +
                            "DIR 1\n" +
                            "PRLINE 560,3\n" +
                            "\n" +
                            "PRPOS 220,290\n" +
                            "DIR 1\n" +
                            "PRLINE 560,3\n" +
                            "\n" +
                            "PRPOS 220,330\n" +
                            "DIR 1\n" +
                            "PRLINE 560,3\n" +
                            "\n" +
                            "PRPOS 220,370\n" +
                            "DIR 1\n" +
                            "PRLINE 560,3\n" +
                            "\n" +
                            "PRPOS 220,410\n" +
                            "DIR 1\n" +
                            "PRLINE 560,3\n" +
                            "\n" +
                            "PRPOS 266,170\n" +
                            "DIR 4\n" +
                            "PRLINE 40,3\n" +
                            "\n" +
                            "PRPOS 533,170\n" +
                            "DIR 4\n" +
                            "PRLINE 40,3\n" +
                            "\n" +
                            "PRPOS 40,265:AN7\n" +
                            "BARSET \"QRCODE\",1,1,6,2,1\n" +
                            "PB \"T00,CG201605ADMIN0154,0001,12\"\n" +
                            "\n" +
                            "PF\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "\n");
            writer.flush();
        }
    }
}
