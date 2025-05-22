package com.example.cauhoi2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MixService {

    /**
     * Đọc file Word (.docx) và trích xuất toàn bộ phần XML nội dung chính
     */
    public String extractXmlFromDocx(MultipartFile file) {
        try {
            // Đọc tài liệu DOCX từ MultipartFile
            InputStream inputStream = file.getInputStream();
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inputStream);

            // Lấy phần chính của tài liệu
            MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();

            // Lấy danh sách các đoạn văn trong tài liệu
            List<Object> paragraphs = mainDocumentPart.getJaxbElement().getBody().getContent();

            // Tạo StringBuilder để lưu trữ XML
            StringBuilder xmlContent = new StringBuilder();

            // Duyệt qua từng đoạn văn và lấy XML của từng câu
            for (Object obj : paragraphs) {
                if (obj instanceof P) {
                    P paragraph = (P) obj;
                    // Lấy XML của đoạn văn (câu)
                    xmlContent.append(marshalToXML(paragraph)).append("\n");
                }
            }

            // Trả về XML của các câu
            return xmlContent.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error extracting XML: " + e.getMessage();
        }
    }

    // Hàm để chuyển đổi đối tượng P thành chuỗi XML
    private String marshalToXML(P paragraph){
        try {
            // Trích xuất XML trực tiếp từ đối tượng P
            String xml = org.docx4j.XmlUtils.marshaltoString(paragraph);
            return xml;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during XML extraction.";
        }
    }

}
