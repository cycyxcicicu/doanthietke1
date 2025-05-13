package com.example.cauhoi2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MixQuestionService1 {

    public record QuestionXMLDTO(String questionXml, List<String> optionXmls) {}

    public List<QuestionXMLDTO> extract(MultipartFile file) {
        List<QuestionXMLDTO> result = new ArrayList<>();
        String fullXml = extractDocumentXml(file);
        String namespaceHeader = extractNamespaceHeader(fullXml);

        Pattern pPattern = Pattern.compile("<w:p[\\s\\S]*?</w:p>");
        Matcher matcher = pPattern.matcher(fullXml);

        String currentQuestion = null;
        List<String> currentOptions = new ArrayList<>();

        while (matcher.find()) {
            String block = matcher.group();

               if (block.contains(">Câu ") && block.contains(".")) {
                if (currentQuestion != null) {
                    result.add(new QuestionXMLDTO(currentQuestion, new ArrayList<>(currentOptions)));
                    currentOptions.clear();
                }
                currentQuestion = block;
            } else {
                boolean isOption = false;
                if (block.matches("(?is).*<w:t[^>]*>\\s*[A-Da-d][\\.|\\)]\\s+.*?</w:t>.*") ||
    block.matches("(?is).*<w:t[^>]*>\\s*[A-Da-d][\\.|\\)]\\s*</w:t>.*") ||
    block.contains("<w:object") ||
    block.contains("<v:shape") ||
    block.contains("<wp:inline")) {
                    isOption = true;
                } else {
                    
                    Matcher optStart = Pattern.compile("<w:t[^>]*>\\s*([A-Da-d])[\\.|\\)]\\s*</w:t>").matcher(block);
                    if (optStart.find()) {
                        isOption = true;
                    }
                }

                if (isOption) {
                    currentOptions.add(block);
                }
            }
        }

        if (currentQuestion != null) {
            result.add(new QuestionXMLDTO(currentQuestion, currentOptions));
        }

        saveRawXmlToWord(result, namespaceHeader, "D:/Downloads/output.docx", file);
        return result;
    }

    private String extractDocumentXml(MultipartFile file) {
        try (ZipInputStream zip = new ZipInputStream(file.getInputStream())) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if ("word/document.xml".equals(entry.getName())) {
                    return new String(zip.readAllBytes(), StandardCharsets.UTF_8);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đọc document.xml từ DOCX", e);
        }
        throw new RuntimeException("Không tìm thấy document.xml trong file DOCX");
    }

    private String extractNamespaceHeader(String documentXml) {
        Matcher matcher = Pattern.compile("<w:document[^>]+>").matcher(documentXml);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new RuntimeException("Không tìm thấy <w:document ...> trong document.xml");
    }

    public void saveRawXmlToWord(List<QuestionXMLDTO> result, String namespaceHeader, String outputPath, MultipartFile originalDocx) {
        try {
            File tempDir = Files.createTempDirectory("docx_extract").toFile();
            File tempDocx = new File(tempDir, "template.docx");
            originalDocx.transferTo(tempDocx);

            Path unzipPath = tempDir.toPath().resolve("unzipped");
            unzipDocx(tempDocx.toPath(), unzipPath);

            Path documentXml = unzipPath.resolve("word/document.xml");
            try (BufferedWriter writer = Files.newBufferedWriter(documentXml, StandardCharsets.UTF_8)) {
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
                writer.write(namespaceHeader);
                writer.write("\n<w:body>\n");

                for (QuestionXMLDTO dto : result) {
                    writer.write(dto.questionXml());
                    writer.write("\n");
                    for (String opt : dto.optionXmls()) {
                        writer.write(opt);
                        writer.write("\n");
                    }
                }

                writer.write("</w:body>\n</w:document>");
            }

            zipFolder(unzipPath, Paths.get(outputPath));
            log.info("✅ Ghi file Word thành công tại: {}", outputPath);

        } catch (Exception e) {
            log.error("❌ Ghi Word bằng BufferedWriter thất bại", e);
        }
    }

    private void unzipDocx(Path docxPath, Path destDir) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(docxPath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path newPath = destDir.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(newPath);
                } else {
                    Files.createDirectories(newPath.getParent());
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    private void zipFolder(Path sourceDir, Path zipPath) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            Files.walk(sourceDir).filter(Files::isRegularFile).forEach(path -> {
                try {
                    String zipEntryName = sourceDir.relativize(path).toString().replace("\\", "/");
                    zos.putNextEntry(new ZipEntry(zipEntryName));
                    Files.copy(path, zos);
                    zos.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
} 
