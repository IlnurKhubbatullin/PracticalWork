package com.example.practicalwork.converters;

import com.example.practicalwork.models.Document;
import com.example.practicalwork.repositories.DocFileRepository;
import com.example.practicalwork.utils.FileNameCreator;
import com.example.practicalwork.utils.ZipFileCreator;
import lombok.AllArgsConstructor;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.*;

@Service
@AllArgsConstructor
public class ToDocxConverter {
    private DocFileRepository docFileRepository;
    private FileNameCreator fileNameCreator;
    private ZipFileCreator zipFileCreator;
    public void convert(Document doc) throws IOException {

        // Create document content
//        StringBuilder fileText = new StringBuilder();
//        List<DocField> fields = doc.getFields();
//
//        for (DocField f : fields) {
//            fileText.append(f.getDefaultValue()).append("\n");
//        }

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
            title.setAlignment(ParagraphAlignment.CENTER);

            // Set title font
            XWPFRun titleRun = title.createRun();
            titleRun.setTextPosition(20);
            titleRun.setBold(true);
            titleRun.setFontSize(22);

            titleRun.setText(doc.getDocTitle().getLabel() + " №" + doc.getNumber());

            // Create top table
            XWPFTable tableTop = xwpfDocument.createTable(1, 2);

            // Write to first row, first column
            XWPFParagraph p1 = tableTop.getRow(0).getCell(0).getParagraphs().get(0);
            p1.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun r1 = p1.createRun();
            r1.setTextPosition(20);
            r1.setBold(false);
            r1.setText("Москва");

            // Write to first row, second column
            XWPFParagraph p2 = tableTop.getRow(0).getCell(1).getParagraphs().get(0);
            p2.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun r2 = p2.createRun();
            r2.setTextPosition(20);
            r2.setBold(false);
            r2.setText(doc.getCreatedAt().toString().substring(0, 10));

            // Create header
            XWPFParagraph header = xwpfDocument.createParagraph();
            header.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun headerRun = header.createRun();
            headerRun.setTextPosition(0);
            headerRun.setBold(false);
            headerRun.setFontSize(12);
            headerRun.setText(doc.getFields().get(0).getDefaultValue());

            // Create title of division1
            XWPFParagraph titleDiv1 = xwpfDocument.createParagraph();
            titleDiv1.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun titleDiv1Run = titleDiv1.createRun();
            titleDiv1Run.setTextPosition(10);
            titleDiv1Run.setBold(true);
            titleDiv1Run.setFontSize(12);
            titleDiv1Run.setText(doc.getFields().get(1).getDefaultValue());

            // Create division1
            XWPFParagraph division1 = xwpfDocument.createParagraph();
            division1.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun div1Run = division1.createRun();
            div1Run.setTextPosition(0);
            div1Run.setBold(false);
            div1Run.setFontSize(12);
            div1Run.setText(doc.getFields().get(2).getDefaultValue());

            // Create bottom table
            XWPFTable tableBottom = xwpfDocument.createTable(2, 2);

            // Write to first row, first column
            XWPFParagraph p11 = tableBottom.getRow(0).getCell(0).getParagraphs().get(0);
            p11.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun p11Run = p11.createRun();
            p11Run.setTextPosition(20);
            p11Run.setBold(false);
            p11Run.setText(doc.getFields().get(3).getDefaultValue());

            // Write to first row, second column
            XWPFParagraph p12 = tableBottom.getRow(0).getCell(1).getParagraphs().get(0);
            p12.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun p12Run = p12.createRun();
            p12Run.setTextPosition(20);
            p12Run.setBold(false);
            p12Run.setText(doc.getFields().get(4).getDefaultValue());

            // Write to second row, first column
            XWPFParagraph p21 = tableBottom.getRow(1).getCell(0).getParagraphs().get(0);
            p21.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun p21Run = p21.createRun();
            p21Run.setTextPosition(20);
            p21Run.setBold(false);
            p21Run.setText(doc.getFields().get(5).getDefaultValue());

            // Write to second row, second column
            XWPFParagraph p22 = tableBottom.getRow(1).getCell(1).getParagraphs().get(0);
            p22.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun p22Run = p22.createRun();
            p22Run.setTextPosition(20);
            p22Run.setBold(false);
            p22Run.setText(doc.getFields().get(6).getDefaultValue());

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

            // Compress file to .zip
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
