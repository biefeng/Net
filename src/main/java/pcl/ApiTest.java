package pcl;

import com.pdfcrowd.*;
import java.io.*;

public class ApiTest {
    public static void main(String[] args) throws IOException, Pdfcrowd.Error {
        try {
            // create the API client instance
            Pdfcrowd.HtmlToImageClient client = new Pdfcrowd.HtmlToImageClient("your_username", "your_apikey");

            // configure the conversion
            client.setOutputFormat("png");

            // run the conversion and write the result to a file
            client.convertFileToFile("D:\\hhhhhhhhhhhhhhhhhhhhhhh.html", "MyLayout.png");
        }
        catch(Pdfcrowd.Error why) {
            // report the error
            System.err.println("Pdfcrowd Error: " + why);

            // handle the exception here or rethrow and handle it at a higher level
            throw why;
        }
        catch(IOException why) {
            // report the error
            System.err.println("IO Error: " + why.getMessage());

            // handle the exception here or rethrow and handle it at a higher level
            throw why;
        }
    }
}