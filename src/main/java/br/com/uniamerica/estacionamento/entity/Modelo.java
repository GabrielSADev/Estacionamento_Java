package br.com.uniamerica.estacionamento.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "modelos", schema = "public")
public class Modelo extends AbstractEntity{

    @Getter @Setter
    @Column(name = "nome", length = 45)
    private String nome;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca")
    private Marca marca;

}
