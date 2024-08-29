package com.sparta.project.delivery.review.repository;

import com.sparta.project.delivery.review.entity.ReviewReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewReportRepository extends JpaRepository<ReviewReport, String> {
}
