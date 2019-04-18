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

import com.net.util.de.TaskPrintUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

/*
 *  Use the Java Print Service API to locate a print service which
 *  can print a GIF-encoded image. A GIF image is printed according to
 *  a job template specified as a set of printing attributes.
 */
public class PrintGIF {

    public static void main(String args[]) throws IOException {

        /* Use the pre-defined flavor for a GIF from an InputStream */
        DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;

        /* Create a set which specifies how the job is to be printed */
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
      //  aset.add(MediaSizeName.NA_LETTER);
        //aset.add(new Copies(1));

        /* Locate print services which can print a GIF in the manner specified */
        PrintService[] pservices =
                PrintServiceLookup.lookupPrintServices(flavor, aset);

        if (pservices.length > 0) {
            /* Create a Print Job */
            //DocPrintJob printJob = pservices[3].createPrintJob();
            for (PrintService service:pservices){
                String name = service.getName();
                if (name.equals("EPSON TM-m30 Receipt")){
                    DocPrintJob printJob = service.createPrintJob();
                    /* Create a Doc implementation to pass the print data */
                    TaskPrintUtil taskPrintUtil = new TaskPrintUtil();
                    Map<String, String> data = new HashMap<>();
                    data.put("skuIndex", "1/1");
                    data.put("dateStr", "2019-03-07 12:32:31");
                    data.put("orderNo", "1100051500000006810");
                    data.put("skuName", "牛肉面");
                    data.put("isPackage", "外送");
                    data.put("qty", String.valueOf(2));
                    InputStream image = taskPrintUtil.getTemplate(data);
                    Doc doc = new InputStreamDoc(null,"d:/doc/test.jpg",flavor);

                    /* Print the doc as specified */
                    try {
                        printJob.print(doc, aset);
                    } catch (PrintException e) {
                        System.err.println(e);
                    }
                }
            }

        } else {
            System.err.println("No suitable printers");
        }
    }
}

class InputStreamDoc implements Doc {
    private String filename;

    private DocFlavor docFlavor;
    private InputStream stream;

    public InputStreamDoc(InputStream inputStream,String name, DocFlavor flavor) {
        filename = name;
        docFlavor = flavor;
        this.stream = inputStream;
    }

    public DocFlavor getDocFlavor() {
        return docFlavor;
    }

    /* No attributes attached to this Doc - mainly useful for MultiDoc */
    public DocAttributeSet getAttributes() {
        return null;
    }

    /* Since the data is to be supplied as an InputStream delegate to
     * getStreamForBytes().
     */
    public Object getPrintData() throws IOException {
        return getStreamForBytes();
    }

    /* Not possible to return a GIF as text */
    public Reader getReaderForText()
            throws UnsupportedEncodingException, IOException {
        return null;
    }

    /* Return the print data as an InputStream.
     * Always return the same instance.
     */
    public InputStream getStreamForBytes() throws IOException {
        synchronized(this) {
            if (stream == null) {
                stream = new FileInputStream(filename);
            }
            return stream;
        }
    }
}