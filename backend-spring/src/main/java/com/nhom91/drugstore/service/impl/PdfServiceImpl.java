package com.nhom91.drugstore.service.impl;

import com.nhom91.drugstore.request.CreatePdfRequest;
import com.nhom91.drugstore.dto.PdfDTO;
import com.nhom91.drugstore.dto.PdfReviewDTO;
import com.nhom91.drugstore.request.PdfReviewRequest;
import com.nhom91.drugstore.request.UpdatePdfRequest;
import com.nhom91.drugstore.entity.Pdf;
import com.nhom91.drugstore.entity.PdfReview;
import com.nhom91.drugstore.entity.User;
import com.nhom91.drugstore.repository.PdfRepository;
import com.nhom91.drugstore.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {

    private final PdfRepository pdfRepository;

    @Override
    public List<PdfDTO> getAllPdfs() {
        return pdfRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PdfDTO getPdfById(Long id) {
        Pdf pdf = pdfRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pdf not found"));
        return convertToDTO(pdf);
    }

    @Override
    @Transactional
    public void deletePdf(Long id) {
        Pdf pdf = pdfRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pdf not found"));
        pdfRepository.delete(pdf);
    }

    @Override
    @Transactional
    public PdfDTO createPdf(CreatePdfRequest request, Long userId) {
        // Check if pdf name exists
        List<Pdf> existing = pdfRepository.findAll().stream()
                .filter(p -> p.getName().equals(request.getName()))
                .collect(Collectors.toList());
        if (!existing.isEmpty()) {
            throw new RuntimeException("Pdf name already exists");
        }

        Pdf pdf = new Pdf();
        pdf.setName(request.getName());
        pdf.setImage(request.getImage());
        pdf.setFile(request.getFile());

        Pdf savedPdf = pdfRepository.save(pdf);
        return convertToDTO(savedPdf);
    }

    @Override
    @Transactional
    public PdfDTO updatePdf(Long id, UpdatePdfRequest request) {
        Pdf pdf = pdfRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pdf not found"));

        if (request.getName() != null) pdf.setName(request.getName());
        if (request.getImage() != null) pdf.setImage(request.getImage());
        if (request.getFile() != null) pdf.setFile(request.getFile());

        Pdf updatedPdf = pdfRepository.save(pdf);
        return convertToDTO(updatedPdf);
    }

    @Override
    public List<PdfDTO> searchPdfs(String type) {
        return pdfRepository.findAll().stream()
                .filter(pdf -> pdf.getName().toLowerCase().contains(type.toLowerCase()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addPdfReview(Long pdfId, PdfReviewRequest request, User user) {
        Pdf pdf = pdfRepository.findById(pdfId)
                .orElseThrow(() -> new RuntimeException("Pdf not found"));

        // Check if user already reviewed
        boolean alreadyReviewed = pdf.getReviews().stream()
                .anyMatch(review -> review.getUser().getId().equals(user.getId()));
        if (alreadyReviewed) {
            throw new RuntimeException("Pdf already reviewed");
        }

        PdfReview review = new PdfReview();
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setUser(user);
        review.setPdf(pdf);

        pdf.getReviews().add(review);
        updatePdfRating(pdf);

        pdfRepository.save(pdf);
    }

    private void updatePdfRating(Pdf pdf) {
        if (!pdf.getReviews().isEmpty()) {
            double avgRating = pdf.getReviews().stream()
                    .mapToInt(PdfReview::getRating)
                    .average()
                    .orElse(0.0);
            pdf.setRating(BigDecimal.valueOf(avgRating).setScale(2, RoundingMode.HALF_UP));
            pdf.setNumReviews(pdf.getReviews().size());
        }
    }

    private PdfDTO convertToDTO(Pdf pdf) {
        List<PdfReviewDTO> reviewDTOs = pdf.getReviews().stream()
                .map(review -> new PdfReviewDTO(review.getId(), review.getRating(), review.getComment(),
                        review.getUser().getName(), review.getUser().getId(), review.getCreatedAt()))
                .collect(Collectors.toList());

        return new PdfDTO(pdf.getId(), pdf.getName(), pdf.getImage(), pdf.getFile(),
                reviewDTOs, pdf.getRating(), pdf.getNumReviews(), pdf.getCreatedAt(), pdf.getUpdatedAt());
    }
}
