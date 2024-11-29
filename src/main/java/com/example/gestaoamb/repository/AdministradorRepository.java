package com.example.gestaoamb.repository;

import com.example.gestaoamb.model.Administrador;
import com.example.gestaoamb.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository <Administrador, Long> {
}
