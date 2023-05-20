package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Instant;

@Service
public class MovimentacaoService {
    @Autowired
    MovimentacaoRep movimentacaoRep;


    @Transactional(rollbackFor = Exception.class)
    public void verificarMovimentacao(final Movimentacao movimentacao){

        Assert.isTrue(!movimentacao.getVeiculo().equals(""),"O veiculo nao pode ser nulo!");
        Movimentacao veiculoExistente = movimentacaoRep.findByVeiculo(movimentacao.getVeiculo());
        Assert.isTrue(veiculoExistente == null || veiculoExistente.equals(movimentacao),"Veiculo já existente");

        Assert.isTrue(!movimentacao.getCondutor().equals(""),"O condutor nao pode ser nulo!");

        Assert.isTrue(!movimentacao.getEntrada().equals(""),"A entrada não pode ser nula!");

        movimentacao.setAtivo(true);

        this.movimentacaoRep.save(movimentacao);
    }

    public void atualizarMovimentacao (Movimentacao movimentacao){
        final Movimentacao moviAttService=this.movimentacaoRep.findById(movimentacao.getId()).orElse(null);
        movimentacao.setCadastro(moviAttService.getCadastro());

        this.movimentacaoRep.save(movimentacao);

    }

}
