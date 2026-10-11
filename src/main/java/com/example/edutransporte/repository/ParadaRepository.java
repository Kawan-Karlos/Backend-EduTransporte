package com.example.edutransporte.repository;

import com.example.edutransporte.model.Parada;
import com.example.edutransporte.model.id.ParadaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, ParadaId> {

    // Paradas da viagem na ordem do trajeto
    @Query("select p from Parada p where p.viagem.id = :viagemId order by p.id.ordem asc")
    List<Parada> findByViagemOrdenadas(@Param("viagemId") Integer viagemId);
}
