package com.nhom91.drugstore.service;

import com.nhom91.drugstore.dto.*;
import com.nhom91.drugstore.entity.User;
import com.nhom91.drugstore.request.CreatePdfRequest;
import com.nhom91.drugstore.request.PdfReviewRequest;
import com.nhom91.drugstore.request.UpdatePdfRequest;

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