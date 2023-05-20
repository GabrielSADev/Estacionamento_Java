package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "marcas", schema = "public")
public class Marca extends AbstractEntity{

    @Getter @Setter
    @Column(name = "nomeMarca", nullable = false, unique = true, length = 15)
    private String nome;

}
