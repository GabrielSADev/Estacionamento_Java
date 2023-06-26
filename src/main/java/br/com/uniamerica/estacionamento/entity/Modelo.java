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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "modelo_marca",
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {
                            "modelo_id",
                            "marca_id"
                    }
            ),
            joinColumns = @JoinColumn(
                    name = "modelo_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "marca_id"
            )
    )
    private Marca marca;

}
