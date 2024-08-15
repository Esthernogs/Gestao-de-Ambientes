package com.example.gestaoamb.repository;

import com.example.gestaoamb.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository <Professor, Long> {
}
