package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRep extends JpaRepository <Veiculo,Long> {
    Veiculo findByPlaca(String placa);
}
