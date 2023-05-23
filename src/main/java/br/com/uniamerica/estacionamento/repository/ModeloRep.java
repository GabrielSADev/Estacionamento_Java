package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeloRep extends JpaRepository <Modelo,Long> {

    List<Modelo> findByAtivo(boolean ativo);
}
