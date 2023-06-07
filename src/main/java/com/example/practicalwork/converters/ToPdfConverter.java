package com.example.practicalwork.converters;

import com.example.practicalwork.models.Document;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ToPdfConverter {
    private final ToDocxConverter toDocxConverter;
    public void convert(Document doc) {

//        toDocxConverter.convert(doc);

//    try {
//        FileInputStream docFile = new FileInputStream(uri);
//        XWPFDocument xwpfDocument = new XWPFDocument(docFile);
//        PdfOptions pdfOptions = PdfOptions.create();
//        OutputStream out = new FileOutputStream("tmp.pdf");
//        PdfConverter.getInstance().convert(xwpfDocument, out, pdfOptions);
//        xwpfDocument.close();
//        out.close();
//    }
//    catch (Exception e) {
//        e.printStackTrace();
//    }
        System.out.println("Converted to pdf!");
    }
}
