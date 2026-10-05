package com.agencia.viagens.controller;

import com.agencia.viagens.model.Destino;
import com.agencia.viagens.model.Hotel;
import com.agencia.viagens.repository.DestinoRepository;
import com.agencia.viagens.repository.HotelRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hoteis")
@RequiredArgsConstructor
public class HotelController {

    private final HotelRepository hotelRepository;
    private final DestinoRepository destinoRepository;

    @GetMapping
    public List<Hotel> listar() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> buscarPorId(@PathVariable Long id) {
        return hotelRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/destino/{destinoId}")
    public List<Hotel> listarPorDestino(@PathVariable Long destinoId) {
        return hotelRepository.findByDestinoId(destinoId);
    }

    @PostMapping("/destino/{destinoId}")
    public ResponseEntity<?> criar(@PathVariable Long destinoId, @Valid @RequestBody Hotel hotel) {
        Destino destino = destinoRepository.findById(destinoId).orElse(null);
        if (destino == null) {
            return ResponseEntity.badRequest().body("Destino não encontrado");
        }
        hotel.setDestino(destino);
        Hotel salvo = hotelRepository.save(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> atualizar(@PathVariable Long id, @Valid @RequestBody Hotel dados) {
        return hotelRepository.findById(id)
                .map(existente -> {
                    existente.setNome(dados.getNome());
                    existente.setEstrelas(dados.getEstrelas());
                    existente.setDiaria(dados.getDiaria());
                    existente.setVagasDisponiveis(dados.getVagasDisponiveis());
                    return ResponseEntity.ok(hotelRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!hotelRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        hotelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
