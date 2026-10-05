package com.agencia.viagens.controller;

import com.agencia.viagens.model.Atividade;
import com.agencia.viagens.model.PacoteViagem;
import com.agencia.viagens.repository.AtividadeRepository;
import com.agencia.viagens.repository.PacoteViagemRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades")
@RequiredArgsConstructor
public class AtividadeController {

    private final AtividadeRepository atividadeRepository;
    private final PacoteViagemRepository pacoteViagemRepository;

    @GetMapping
    public List<Atividade> listar() {
        return atividadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atividade> buscarPorId(@PathVariable Long id) {
        return atividadeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pacote/{pacoteId}")
    public List<Atividade> listarPorPacote(@PathVariable Long pacoteId) {
        return atividadeRepository.findByPacoteId(pacoteId);
    }

    @PostMapping("/pacote/{pacoteId}")
    public ResponseEntity<?> criar(@PathVariable Long pacoteId, @Valid @RequestBody Atividade atividade) {
        PacoteViagem pacote = pacoteViagemRepository.findById(pacoteId).orElse(null);
        if (pacote == null) {
            return ResponseEntity.badRequest().body("Pacote não encontrado");
        }
        atividade.setPacote(pacote);
        Atividade salva = atividadeRepository.save(atividade);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atividade> atualizar(@PathVariable Long id, @Valid @RequestBody Atividade dados) {
        return atividadeRepository.findById(id)
                .map(existente -> {
                    existente.setNome(dados.getNome());
                    existente.setDescricao(dados.getDescricao());
                    existente.setPreco(dados.getPreco());
                    return ResponseEntity.ok(atividadeRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!atividadeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        atividadeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
