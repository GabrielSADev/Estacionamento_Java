package br.com.uniamerica.estacionamento.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Table(name = "veiculos", schema = "public")
public class Veiculo extends AbstractEntity {

    @Getter @Setter
    @Column(name = "placa", nullable = false,length = 15, unique = true)
    private String placa;
    @Getter @Setter
    @Column(name = "ano", nullable = false)
    private int ano;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modelo",nullable = false)
    private Modelo modelo;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "cor", length =20, nullable = false)
    private Cor cor;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "tipo", length = 6, nullable = false)
    private Tipo tipo;
}
