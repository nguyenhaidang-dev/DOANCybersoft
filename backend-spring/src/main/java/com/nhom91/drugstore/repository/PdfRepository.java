package com.nhom91.drugstore.repository;

import com.nhom91.drugstore.entity.Pdf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfRepository extends JpaRepository<Pdf, Long> {
}