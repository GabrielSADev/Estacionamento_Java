package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRep extends JpaRepository <Movimentacao,Long> {
    Movimentacao findByVeiculo(Veiculo veiculo);
}
