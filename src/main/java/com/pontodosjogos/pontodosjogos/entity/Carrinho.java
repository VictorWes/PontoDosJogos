package com.pontodosjogos.pontodosjogos.entity;

import com.pontodosjogos.pontodosjogos.enums.StatusCarrinho;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "carrinho")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusCarrinho statusCarrinho;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
