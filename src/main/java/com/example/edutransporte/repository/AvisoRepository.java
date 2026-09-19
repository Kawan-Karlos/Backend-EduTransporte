package com.example.edutransporte.repository;

import com.example.edutransporte.model.Aviso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisoRepository extends JpaRepository<Aviso, Long> {
}