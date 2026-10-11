package com.example.edutransporte.repository;

import com.example.edutransporte.model.Aviso;
import com.example.edutransporte.model.enums.TipoAviso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvisoRepository extends JpaRepository<Aviso, Integer> {

    List<Aviso> findByAtivoTrueOrderByDataDesc();

    List<Aviso> findByTipoAndAtivoTrueOrderByDataDesc(TipoAviso tipo);
}
