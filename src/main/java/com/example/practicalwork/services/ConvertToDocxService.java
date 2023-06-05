package com.example.practicalwork.services;

import com.example.practicalwork.models.DocField;
import com.example.practicalwork.models.Document;
import lombok.AllArgsConstructor;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ConvertToDocxService {
    private DocumentService docService;
    public void createDocx(Long id) throws IOException {

        Document doc = docService.read(id);

        // Create document content
        StringBuilder fileText = new StringBuilder();
        List<DocField> fields = doc.getFields();

        for (DocField f : fields) {
            fileText.append(f.getDefaultValue()).append("\n");
        }

        // Create filename
        StringBuilder fileName = new StringBuilder();
        fileName.append(doc.getDocTitle())
                .append("_")
                .append(doc.getNumber())
                .append("_")
                .append(doc.getCreatedAt().toString(), 0, 10)
                .append(".docx");

        try (XWPFDocument docFile = new XWPFDocument()) {

            // Create document header and footer
            XWPFHeader head = docFile.createHeader(HeaderFooterType.DEFAULT);
            head.createParagraph()
                    .createRun()
//                    .setFontFamily("Courier")
                    .setText("Maxima");

            XWPFFooter foot = docFile.createFooter(HeaderFooterType.DEFAULT);
            foot.createParagraph()
                    .createRun()
//                    .setFontFamily("Courier")
                    .setText(doc.getCreatedAt().toString().substring(0, 10));

            // Create a title
            XWPFParagraph title = docFile.createParagraph();
            title.setAlignment(ParagraphAlignment.RIGHT);

            // Set title font
            XWPFRun titleRun = title.createRun();
            titleRun.setBold(true);
            titleRun.setItalic(false);
            titleRun.setFontSize(22);
            titleRun.setFontFamily("Courier");
            titleRun.setTextPosition(20);
            titleRun.setColor("050505");
            titleRun.setText(doc.getDocTitle().toString());

            // Create document text
            XWPFParagraph paragraph = docFile.createParagraph();
            title.setAlignment(ParagraphAlignment.LEFT);

            // Set paragraph font
            XWPFRun pRun = paragraph.createRun();
            pRun.setBold(false);
            pRun.setItalic(false);
            pRun.setFontSize(12);
            pRun.setFontFamily("Courier");
            pRun.setTextPosition(0);
            pRun.setColor("050505");
            pRun.setUnderline(UnderlinePatterns.NONE);
            pRun.setText(fileText.toString());

            // Save it to .docx file
            try (FileOutputStream out = new FileOutputStream(fileName.toString())) {
                docFile.write(out);
            }

        }
    }
}
