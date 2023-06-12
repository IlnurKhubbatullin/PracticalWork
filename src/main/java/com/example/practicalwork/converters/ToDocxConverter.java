package com.example.practicalwork.converters;

import com.example.practicalwork.models.DocField;
import com.example.practicalwork.models.Document;
import com.example.practicalwork.repositories.DocFileRepository;
import com.example.practicalwork.services.DocumentService;
import com.example.practicalwork.utils.FileNameCreator;
import com.example.practicalwork.utils.ZipFileCreator;
import lombok.AllArgsConstructor;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
@AllArgsConstructor
public class ToDocxConverter {
    private DocumentService docFileService;
    private DocFileRepository docFileRepository;
    private FileNameCreator fileNameCreator;
    private ZipFileCreator zipFileCreator;
    public void convert(Document doc) throws IOException {

        // Create document content
        StringBuilder fileText = new StringBuilder();
        List<DocField> fields = doc.getFields();

        for (DocField f : fields) {
            fileText.append(f.getDefaultValue()).append("\n");
        }

        try (XWPFDocument xwpfDocument = new XWPFDocument()) {

            // Create document header and footer
            XWPFHeader head = xwpfDocument.createHeader(HeaderFooterType.DEFAULT);
            head.createParagraph()
                    .createRun()
                    .setText("Maxima");

            XWPFFooter foot = xwpfDocument.createFooter(HeaderFooterType.DEFAULT);
            foot.createParagraph()
                    .createRun()
                    .setText(doc.getCreatedAt().toString().substring(0, 10));

            // Create a title
            XWPFParagraph title = xwpfDocument.createParagraph();
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
            XWPFParagraph paragraph = xwpfDocument.createParagraph();
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

            // Create file .docx
            String fileName = fileNameCreator.create(doc);
            String fileNameDocx = fileName + ".docx";
            try (FileOutputStream fos = new FileOutputStream(fileNameDocx)) {
                xwpfDocument.write(fos);
                doc.getFile().setName(fileNameDocx);
                docFileRepository.save(doc.getFile());
            } catch(IOException e) {
                System.out.println("IOException : " + e);
            }

            // Save file to .zip file
            if (doc.getFile().isZip()) {
                try {
                    zipFileCreator.create(fileNameDocx, fileName + ".zip");
                    doc.getFile().setName(fileName + ".zip");
                    docFileRepository.save(doc.getFile());
                } catch(IOException e) {
                    System.out.println("IOException : " + e);
                }
            }


        }
    }
}
