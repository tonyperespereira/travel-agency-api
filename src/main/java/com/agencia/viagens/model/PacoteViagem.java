package com.agencia.viagens.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pacotes_viagem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacoteViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @ManyToOne
    @JoinColumn(name = "destino_id", nullable = false)
    @JsonIgnore
    private Destino destino;

    @OneToMany(mappedBy = "pacote", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Atividade> atividades;
}
