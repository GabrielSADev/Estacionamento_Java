package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.math.BigDecimal;

@Entity
@Table(name = "Movimentações", schema = "public")
public class Movimentacao extends AbstractEntity{
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo",nullable = false,unique = true)
    private Veiculo veiculo;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condutor",nullable = false)
    private Condutor condutor;
    @Getter @Setter
    @Column(name = "Entrada",nullable = false)
    private LocalDateTime entrada;
    @Getter @Setter
    @Column(name = "Saida")
    private LocalDateTime saida;
    @Getter @Setter
    @Column(name = "tempo")
    private LocalTime tempo;
    @Getter @Setter
    @Column(name = "tempoDesconto")
    private LocalTime tempoDesconto;
    @Getter @Setter
    @Column(name = "tempoMulta")
    private LocalTime tempoMulta;
    @Getter @Setter
    @Column(name = "valorDesconto")
    private BigDecimal valorDesconto;
    @Getter @Setter
    @Column(name = "valorMulta")
    private BigDecimal valorMulta;
    @Getter @Setter
    @Column(name = "valorTotal")
    private BigDecimal valorTotal;
    @Getter @Setter
    @Column(name = "valorHora")
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "valorHoraMulta")
    private BigDecimal valorHoraMulta;


}


