package com.sparta.project.delivery.ai.repository;

import com.sparta.project.delivery.ai.Entity.Ai;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<Ai, String> {
}
