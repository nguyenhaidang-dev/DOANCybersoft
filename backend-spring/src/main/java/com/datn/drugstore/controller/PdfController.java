package com.datn.drugstore.controller;

import com.datn.drugstore.dto.PdfDTO;
import com.datn.drugstore.entity.User;
import com.datn.drugstore.request.CreatePdfRequest;
import com.datn.drugstore.request.PdfReviewRequest;
import com.datn.drugstore.response.BaseResponse;
import com.datn.drugstore.service.PdfService;
import com.datn.drugstore.request.UpdatePdfRequest;
import com.datn.drugstore.utils.ResponseFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllPdfs() {
        List<PdfDTO> pdfs = pdfService.getAllPdfs();
        return ResponseFactory.success(pdfs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getPdfById(@PathVariable Long id) {
        PdfDTO pdf = pdfService.getPdfById(id);
        return ResponseFactory.success(pdf);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse> deletePdf(@PathVariable Long id) {
        pdfService.deletePdf(id);
        return ResponseFactory.successMessage("Xóa PDF thành công");
    }

    @GetMapping("/searchpdf/{type}")
    public ResponseEntity<BaseResponse> searchPdfs(@PathVariable String type) {
        List<PdfDTO> pdfs = pdfService.searchPdfs(type);
        return ResponseFactory.success(pdfs);
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<BaseResponse> addPdfReview(
            @PathVariable Long id,
            @Valid @RequestBody PdfReviewRequest request,
            @AuthenticationPrincipal User user) {
        pdfService.addPdfReview(id, request, user);
        return ResponseFactory.successMessage("Đánh giá đã được thêm");
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse> createPdf(
            @Valid @RequestBody CreatePdfRequest request,
            @AuthenticationPrincipal User user) {
        PdfDTO pdf = pdfService.createPdf(request, user.getId());
        return ResponseFactory.created(pdf);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse> updatePdf(
            @PathVariable Long id,
            @RequestBody UpdatePdfRequest request) {
        PdfDTO pdf = pdfService.updatePdf(id, request);
        return ResponseFactory.success(pdf, "Cập nhật PDF thành công");
    }
}
