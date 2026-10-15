package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.*;
import com.example.edutransporte.model.*;
import com.example.edutransporte.model.id.ParadaId;
import com.example.edutransporte.model.id.PresencaId;
import com.example.edutransporte.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ViagemService {

    private final ViagemRepository viagemRepository;
    private final MotoristaRepository motoristaRepository;
    private final VeiculoRepository veiculoRepository;
    private final ParadaRepository paradaRepository;
    private final PresencaRepository presencaRepository;
    private final AlunoRepository alunoRepository;

    public ViagemService(ViagemRepository viagemRepository,
                         MotoristaRepository motoristaRepository,
                         VeiculoRepository veiculoRepository,
                         ParadaRepository paradaRepository,
                         PresencaRepository presencaRepository,
                         AlunoRepository alunoRepository) {
        this.viagemRepository = viagemRepository;
        this.motoristaRepository = motoristaRepository;
        this.veiculoRepository = veiculoRepository;
        this.paradaRepository = paradaRepository;
        this.presencaRepository = presencaRepository;
        this.alunoRepository = alunoRepository;
    }

    // ---------- Viagens ----------
    @Transactional(readOnly = true)
    public List<ViagemResponse> listarTodas() {
        return viagemRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Optional<ViagemResponse> buscarPorId(Integer id) {
        return viagemRepository.findById(id).map(this::toResponse);
    }

    @Transactional
    public ViagemResponse criar(ViagemRequest r) {
        Motorista motorista = motoristaRepository.findById(r.motoristaCpf())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Motorista não encontrado"));
        Veiculo veiculo = veiculoRepository.findById(r.veiculoPlaca())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veículo não encontrado"));

        Viagem v = new Viagem();
        v.setNomeRota(r.nomeRota());
        v.setTipo(r.tipo());
        v.setData(r.data());
        v.setHorario(r.horario());
        v.setMotorista(motorista);
        v.setVeiculo(veiculo);
        return toResponse(viagemRepository.save(v));
    }

    public void deletar(Integer id) {
        viagemRepository.deleteById(id);
    }

    // ---------- Paradas ----------
    @Transactional(readOnly = true)
    public List<ParadaResponse> listarParadas(Integer viagemId) {
        return paradaRepository.listarPorViagem(viagemId).stream()
                .map(p -> new ParadaResponse(p.getId().getOrdem(), p.getNome(),
                        p.getLatitude(), p.getLongitude()))
                .toList();
    }

    @Transactional
    public ParadaResponse adicionarParada(Integer viagemId, ParadaRequest r) {
        Viagem viagem = buscarViagem(viagemId);
        ParadaId id = new ParadaId(viagemId, r.ordem());
        if (paradaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe parada com essa ordem");
        }
        Parada p = new Parada();
        p.setId(id);
        p.setViagem(viagem);
        p.setNome(r.nome());
        p.setLatitude(r.latitude());
        p.setLongitude(r.longitude());
        paradaRepository.save(p);
        return new ParadaResponse(r.ordem(), r.nome(), r.latitude(), r.longitude());
    }

    // ---------- Presença ----------
    @Transactional(readOnly = true)
    public List<PresencaResponse> listarPresencas(Integer viagemId) {
        return presencaRepository.findByViagem(viagemId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public PresencaResponse registrarPresenca(Integer viagemId, PresencaRequest r) {
        Viagem viagem = buscarViagem(viagemId);
        Aluno aluno = alunoRepository.findById(r.alunoCpf())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aluno não encontrado"));

        PresencaId id = new PresencaId(r.alunoCpf(), viagemId);
        Presenca p = presencaRepository.findById(id).orElseGet(() -> {
            Presenca nova = new Presenca();
            nova.setId(id);
            nova.setAluno(aluno);
            nova.setViagem(viagem);
            return nova;
        });
        p.setPresente(r.presente() == null ? Boolean.TRUE : r.presente());
        return toResponse(presencaRepository.save(p));
    }

    // ---------- Auxiliares ----------
    private Viagem buscarViagem(Integer id) {
        return viagemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada"));
    }

    private ViagemResponse toResponse(Viagem v) {
        return new ViagemResponse(v.getId(), v.getNomeRota(), v.getTipo(), v.getData(), v.getHorario(),
                v.getMotorista().getCpf(), v.getMotorista().getNomeCompleto(),
                v.getVeiculo().getPlaca(), v.getVeiculo().getModelo());
    }

    private PresencaResponse toResponse(Presenca p) {
        return new PresencaResponse(p.getAluno().getCpf(), p.getAluno().getNomeCompleto(),
                p.getViagem().getId(), p.getPresente(), p.getConfirmadoEm());
    }
}