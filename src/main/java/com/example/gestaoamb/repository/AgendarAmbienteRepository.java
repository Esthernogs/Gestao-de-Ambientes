package com.example.gestaoamb.repository;

import com.example.gestaoamb.model.AgendarAmbiente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendarAmbienteRepository extends JpaRepository <AgendarAmbiente, Long> {

    List<AgendarAmbiente> findByAmbienteId(Long id);
}
