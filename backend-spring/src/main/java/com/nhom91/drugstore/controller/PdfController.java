package com.nhom91.drugstore.controller;

import com.nhom91.drugstore.dto.CreatePdfRequest;
import com.nhom91.drugstore.dto.PdfDTO;
import com.nhom91.drugstore.dto.PdfReviewRequest;
import com.nhom91.drugstore.dto.UpdatePdfRequest;
import com.nhom91.drugstore.entity.User;
import com.nhom91.drugstore.service.PdfService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;

    @GetMapping("/all")
    public ResponseEntity<List<PdfDTO>> getAllPdfs() {
        List<PdfDTO> pdfs = pdfService.getAllPdfs();
        return ResponseEntity.ok(pdfs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PdfDTO> getPdfById(@PathVariable Long id) {
        PdfDTO pdf = pdfService.getPdfById(id);
        return ResponseEntity.ok(pdf);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deletePdf(@PathVariable Long id) {
        pdfService.deletePdf(id);
        return ResponseEntity.ok(Map.of("message", "Pdf deleted"));
    }

    @GetMapping("/searchpdf/{type}")
    public ResponseEntity<List<PdfDTO>> searchPdfs(@PathVariable String type) {
        List<PdfDTO> pdfs = pdfService.searchPdfs(type);
        return ResponseEntity.ok(pdfs);
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<Map<String, String>> addPdfReview(
            @PathVariable Long id,
            @Valid @RequestBody PdfReviewRequest request,
            @AuthenticationPrincipal User user) {
        pdfService.addPdfReview(id, request, user);
        return ResponseEntity.status(201).body(Map.of("message", "Review added"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PdfDTO> createPdf(
            @Valid @RequestBody CreatePdfRequest request,
            @AuthenticationPrincipal User user) {
        PdfDTO pdf = pdfService.createPdf(request, user.getId());
        return ResponseEntity.status(201).body(pdf);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PdfDTO> updatePdf(
            @PathVariable Long id,
            @RequestBody UpdatePdfRequest request) {
        PdfDTO pdf = pdfService.updatePdf(id, request);
        return ResponseEntity.ok(pdf);
    }

    // Migrated from NodeJS PdfRoutes
}