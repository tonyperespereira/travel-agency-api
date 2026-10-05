package com.agencia.viagens.controller;

import com.agencia.viagens.model.Destino;
import com.agencia.viagens.model.PacoteViagem;
import com.agencia.viagens.repository.DestinoRepository;
import com.agencia.viagens.repository.PacoteViagemRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacotes")
@RequiredArgsConstructor
public class PacoteViagemController {

    private final PacoteViagemRepository pacoteViagemRepository;
    private final DestinoRepository destinoRepository;

    @GetMapping
    public List<PacoteViagem> listar() {
        return pacoteViagemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacoteViagem> buscarPorId(@PathVariable Long id) {
        return pacoteViagemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/destino/{destinoId}")
    public List<PacoteViagem> listarPorDestino(@PathVariable Long destinoId) {
        return pacoteViagemRepository.findByDestinoId(destinoId);
    }

    @PostMapping("/destino/{destinoId}")
    public ResponseEntity<?> criar(@PathVariable Long destinoId, @Valid @RequestBody PacoteViagem pacote) {
        Destino destino = destinoRepository.findById(destinoId).orElse(null);
        if (destino == null) {
            return ResponseEntity.badRequest().body("Destino não encontrado");
        }
        pacote.setDestino(destino);
        PacoteViagem salvo = pacoteViagemRepository.save(pacote);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacoteViagem> atualizar(@PathVariable Long id, @Valid @RequestBody PacoteViagem dados) {
        return pacoteViagemRepository.findById(id)
                .map(existente -> {
                    existente.setNome(dados.getNome());
                    existente.setPreco(dados.getPreco());
                    existente.setDataInicio(dados.getDataInicio());
                    existente.setDataFim(dados.getDataFim());
                    return ResponseEntity.ok(pacoteViagemRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!pacoteViagemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pacoteViagemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
