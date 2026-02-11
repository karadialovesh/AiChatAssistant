package com.example.rag.ai.controller;

import com.example.rag.ai.service.PdfIngestionService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
public class DocumentUploadController {

    private final PdfIngestionService pdfIngestionService;

    public DocumentUploadController(PdfIngestionService pdfIngestionService) {
        this.pdfIngestionService = pdfIngestionService;
    }


    @PostMapping("/upload")
    public String uploadPdf(@RequestParam("file") MultipartFile file, String userId) {
        pdfIngestionService.ingest(file, userId);
        return "PDF uploaded and processed";
    }
}
