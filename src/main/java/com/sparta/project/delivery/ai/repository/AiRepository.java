package com.sparta.project.delivery.ai.repository;

import com.sparta.project.delivery.ai.entity.Ai;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<Ai, String> {
}
