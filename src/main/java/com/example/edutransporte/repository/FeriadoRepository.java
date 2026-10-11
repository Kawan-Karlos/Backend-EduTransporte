package com.example.edutransporte.repository;

import com.example.edutransporte.model.Feriado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FeriadoRepository extends JpaRepository<Feriado, LocalDate> {

    List<Feriado> findByDataBetweenOrderByDataAsc(LocalDate inicio, LocalDate fim);
}
