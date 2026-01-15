package com.datn.drugstore.service;

import com.datn.drugstore.dto.PdfDTO;
import com.datn.drugstore.entity.User;
import com.datn.drugstore.request.CreatePdfRequest;
import com.datn.drugstore.request.PdfReviewRequest;
import com.datn.drugstore.request.UpdatePdfRequest;

import java.util.List;

public interface PdfService {
    List<PdfDTO> getAllPdfs();
    PdfDTO getPdfById(Long id);
    void deletePdf(Long id);
    PdfDTO createPdf(CreatePdfRequest request, Long userId);
    PdfDTO updatePdf(Long id, UpdatePdfRequest request);
    List<PdfDTO> searchPdfs(String type);
    void addPdfReview(Long pdfId, PdfReviewRequest request, User user);
}