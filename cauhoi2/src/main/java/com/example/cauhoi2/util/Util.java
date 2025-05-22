package com.example.cauhoi2.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Util {
    public static MultipartFile createMultipartFile(byte[] content, String fileName) {
        return new MultipartFile() {
            @Override
            public String getName() {
                return "file";
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                return "image/png";
            }

            @Override
            public boolean isEmpty() {
                return content.length == 0;
            }

            @Override
            public long getSize() {
                return content.length;
            }

            @Override
            public byte[] getBytes() {
                return content;
            }

            @Override
            public InputStream getInputStream() {
                return new ByteArrayInputStream(content);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.write(dest.toPath(), content);
            }
        };
    }

    public static int countStr2InStr1(String str1, String str2) {
        if (str1 == null || str1.isEmpty()) return 0;
        if (str2 == null || str2.isEmpty()) return 0;
        int count = 0;
        for (int i = 0; i <= str1.length() - str2.length(); i++) {
            for (int j = 0; j < str2.length(); j++) {
                if (str2.charAt(j) != str1.charAt(i + j))
                    break;
                else if (j == str2.length() - 1)
                    count++;
            }
        }
        return count;
    }
}
