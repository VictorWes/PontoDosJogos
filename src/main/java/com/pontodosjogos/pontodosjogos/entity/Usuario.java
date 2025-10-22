package com.pontodosjogos.pontodosjogos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Carrinho> carrinhos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Endereco> enderecos;



}
