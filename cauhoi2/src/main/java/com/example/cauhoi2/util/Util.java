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

}
