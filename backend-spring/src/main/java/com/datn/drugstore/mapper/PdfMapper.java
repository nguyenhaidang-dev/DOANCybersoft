package com.datn.drugstore.mapper;

import com.datn.drugstore.dto.PdfDTO;
import com.datn.drugstore.entity.Pdf;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PdfMapper {

    public static PdfDTO toDTO(Pdf pdf) {
        if (pdf == null) {
            return null;
        }
        
        PdfDTO dto = new PdfDTO();
        dto.setId(pdf.getId());
        dto.setName(pdf.getName());
        dto.setImage(pdf.getImage());
        dto.setFile(pdf.getFile());
        dto.setRating(pdf.getRating());
        dto.setNumReviews(pdf.getNumReviews());
        dto.setCreatedAt(pdf.getCreatedAt());
        dto.setUpdatedAt(pdf.getUpdatedAt());
        
        // Map reviews if needed (lazy loaded)
        // dto.setReviews(...);
        
        return dto;
    }

    public static List<PdfDTO> toDTOList(List<Pdf> pdfs) {
        if (pdfs == null) {
            return null;
        }
        return pdfs.stream()
                .map(PdfMapper::toDTO)
                .collect(Collectors.toList());
    }
}
