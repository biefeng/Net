package pcl;

import com.net.util.bitmap.ImagePixelUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @author Yoav Aharoni
 */
public class HtmlImageGenerator extends HTMLEditorKit {
    private JEditorPane editorPane;
    static final Dimension DEFAULT_SIZE = new Dimension(1200, 800);

    public HtmlImageGenerator() {
        editorPane = createJEditorPane();
    }

    public ComponentOrientation getOrientation() {
        return editorPane.getComponentOrientation();
    }

    public void setOrientation(ComponentOrientation orientation) {
        editorPane.setComponentOrientation(orientation);
    }

    public Dimension getSize() {
        return editorPane.getSize();
    }

    public void setSize(Dimension dimension) {
        editorPane.setSize(dimension);
    }

    public void loadUrl(URL url) {
        try {
            editorPane.setPage(url);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while loading %s", url), e);
        }
    }

    public void loadUrl(String url) {
        try {
            editorPane.setPage(url);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while loading %s", url), e);
        }
    }

    public void loadHtml(String html) {
        editorPane.setText(html);
        onDocumentLoad();
    }

    protected void onDocumentLoad() {
    }

    public Dimension getDefaultSize() {
        return DEFAULT_SIZE;
    }

    public BufferedImage getBufferedImage() {
        Dimension prefSize = editorPane.getPreferredSize();
        BufferedImage img = new BufferedImage(prefSize.width, editorPane.getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = img.getGraphics();
        editorPane.setSize(prefSize);
        editorPane.paint(graphics);
        return img;
    }

    protected JEditorPane createJEditorPane() {
        final JEditorPane editorPane = new JEditorPane();
        editorPane.setSize(getDefaultSize());
        editorPane.setEditable(false);
        final SynchronousHTMLEditorKit kit = new SynchronousHTMLEditorKit();
        editorPane.setEditorKitForContentType("text/html", kit);
        editorPane.setContentType("text/html");
        editorPane.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("page")) {
                    onDocumentLoad();
                }
            }
        });
        return editorPane;
    }

    public void saveAsImage(String s) throws IOException {
        BufferedImage srcImage = getBufferedImage();
        BufferedImage image = ImagePixelUtil._24to1bit(srcImage);
        ImageIO.write(image, "BMP", new FileOutputStream(s));
    }

    class SynchronousHTMLEditorKit extends HTMLEditorKit {

        public Document createDefaultDocument() {
            HTMLDocument doc = (HTMLDocument) super.createDefaultDocument();
            doc.setAsynchronousLoadPriority(-1);
            return doc;
        }

        public ViewFactory getViewFactory() {
            return new HTMLFactory() {
                public View create(Element elem) {
                    View view = super.create(elem);
                    if (view instanceof ImageView) {
                        ((ImageView) view).setLoadsSynchronously(true);
                    }
                    return view;
                }
            };
        }
    }

}
