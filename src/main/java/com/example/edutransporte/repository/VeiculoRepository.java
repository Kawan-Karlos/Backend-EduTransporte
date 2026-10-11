package com.example.edutransporte.repository;

import com.example.edutransporte.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, String> {

    List<Veiculo> findByModeloContainingIgnoreCase(String modelo);
}
