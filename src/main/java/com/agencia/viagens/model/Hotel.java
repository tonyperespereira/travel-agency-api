package com.agencia.viagens.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hoteis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer estrelas;

    @Column(nullable = false)
    private Double diaria;

    @Column(nullable = false)
    private Integer vagasDisponiveis;

    @ManyToOne
    @JoinColumn(name = "destino_id", nullable = false)
    @JsonIgnore
    private Destino destino;
}
