package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Getter @Setter
    @Column(name = "Cadastro")
    private LocalDateTime cadastro;
    @Getter @Setter
    @Column(name = "edicao")
    private LocalDateTime edicao;
    @Getter @Setter
    @Column(name = "ativo", nullable = false)
    private boolean ativo;

   @PrePersist
    private void prePersist(){
       this.cadastro = LocalDateTime.now();
       this.ativo=true;
   }

   @PreUpdate
   private void preUpdate(){
       this.edicao = LocalDateTime.now();
   }
}
