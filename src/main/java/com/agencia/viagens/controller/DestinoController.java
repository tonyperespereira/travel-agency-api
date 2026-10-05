package com.agencia.viagens.controller;

import com.agencia.viagens.model.Destino;
import com.agencia.viagens.repository.DestinoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinos")
@RequiredArgsConstructor
public class DestinoController {

    private final DestinoRepository destinoRepository;

    @GetMapping
    public List<Destino> listar() {
        return destinoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destino> buscarPorId(@PathVariable Long id) {
        return destinoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Destino> criar(@Valid @RequestBody Destino destino) {
        Destino salvo = destinoRepository.save(destino);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destino> atualizar(@PathVariable Long id, @Valid @RequestBody Destino dados) {
        return destinoRepository.findById(id)
                .map(existente -> {
                    existente.setNome(dados.getNome());
                    existente.setPais(dados.getPais());
                    existente.setDescricao(dados.getDescricao());
                    return ResponseEntity.ok(destinoRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!destinoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        destinoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
