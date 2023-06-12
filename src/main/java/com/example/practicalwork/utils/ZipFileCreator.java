package com.example.practicalwork.utils;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ZipFileCreator {
    public void create(String source, String target) throws IOException {

        FileOutputStream fout = new FileOutputStream(target);
        ZipOutputStream zout = new ZipOutputStream(fout);

        FileInputStream fin = new FileInputStream(source);
        ZipEntry zipEntry = new ZipEntry(source);
        zout.putNextEntry(zipEntry);
        int length;
        byte[] buffer = new byte[1024];
        while((length = fin.read(buffer)) > 0) {
            zout.write(buffer, 0, length);
        }

        zout.closeEntry();
        zout.finish();
        fin.close();
        zout.close();

    }
}
