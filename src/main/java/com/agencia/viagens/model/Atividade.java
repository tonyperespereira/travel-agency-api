package com.agencia.viagens.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "atividades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false)
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "pacote_id", nullable = false)
    @JsonIgnore
    private PacoteViagem pacote;
}
