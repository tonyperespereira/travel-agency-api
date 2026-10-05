package com.agencia.viagens.repository;

import com.agencia.viagens.model.PacoteViagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacoteViagemRepository extends JpaRepository<PacoteViagem, Long> {
    List<PacoteViagem> findByDestinoId(Long destinoId);
}
