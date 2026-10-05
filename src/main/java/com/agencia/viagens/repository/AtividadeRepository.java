package com.agencia.viagens.repository;

import com.agencia.viagens.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    List<Atividade> findByPacoteId(Long pacoteId);
}
