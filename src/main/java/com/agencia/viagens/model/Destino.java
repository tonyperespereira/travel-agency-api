package com.agencia.viagens.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "destinos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String pais;

    @Column(length = 1000)
    private String descricao;

    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Hotel> hoteis;

    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PacoteViagem> pacotes;
}
