package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoRep extends JpaRepository <Veiculo,Long> {
    Veiculo findByPlaca(String placa);

    List<Veiculo> findByAtivo(boolean ativo);
}
