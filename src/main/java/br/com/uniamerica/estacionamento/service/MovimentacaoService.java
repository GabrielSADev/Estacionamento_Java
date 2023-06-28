package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;

import br.com.uniamerica.estacionamento.repository.VeiculoRep;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRep;
import br.com.uniamerica.estacionamento.repository.CondutorRep;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRep;

import br.com.uniamerica.estacionamento.service.ConfiguracaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class MovimentacaoService {
    @Autowired
    MovimentacaoRep movimentacaoRep;

    @Autowired
    ConfiguracaoService configuracaoService;

    @Autowired
    CondutorRep condutorRep;

    Configuracao configuracao;

    @Transactional(rollbackFor = Exception.class)
    public void verificarMovimentacao(Movimentacao movimentacao){

        movimentacao.setEntrada(LocalDateTime.now());
        Assert.isTrue(!movimentacao.getVeiculo().equals(""),"O veiculo nao pode ser nulo!");

        Assert.isTrue(!movimentacao.getCondutor().equals(""),"O condutor nao pode ser nulo!");

        Assert.isTrue(!movimentacao.getEntrada().equals(""),"A entrada não pode ser nula!");

        movimentacao.setAtivo(true);

        this.movimentacaoRep.save(movimentacao);
    }

    public ResponseEntity<?> atualizarMovimentacao (Movimentacao movimentacao, Long id){

        movimentacao.setSaida(LocalDateTime.now());

        final Movimentacao movimentacao1 = this.movimentacaoRep.findById(id).orElse(null);

        Assert.isTrue(movimentacao.getVeiculo() != null,"Veiculo não pode ser nulo");

        Assert.isTrue(movimentacao.getCondutor() != null,  "Condutor não pode ser nulo");

        Duration valorEntre = Duration.between(movimentacao.getEntrada(), movimentacao.getSaida());

        String stringHoras = String.format("%02d" , valorEntre.toHoursPart());
        String stringMinutos = String.format("%02d" , valorEntre.toMinutesPart());
        String stringSegundos = String.format("%02d" , valorEntre.toSecondsPart());

        float paraHorasM = Float.parseFloat(stringMinutos);
        float minutosToHours = paraHorasM / 60;

        float paraHorasS = Float.parseFloat(stringSegundos);
        float secondsToHours = paraHorasS / 3600;

        float paraHorasH = Float.parseFloat(stringHoras);

        float pegadorHoras = configuracaoService.PegaHoras;

        System.out.println(pegadorHoras);
        Assert.isTrue(pegadorHoras != 0.0, "Adiciona um valor para valorHora");

        float taDevendo = (secondsToHours + minutosToHours + paraHorasH) * pegadorHoras;

        System.out.println(taDevendo);
        movimentacao.setAtivo(false);

        this.movimentacaoRep.save(movimentacao);

        return ResponseEntity.ok("Horas a pagar: "+ (secondsToHours + minutosToHours + paraHorasH) +"\n Total a pagar: " + taDevendo
                + "\n Hora da entrada: " + movimentacao.getEntrada() + "\n Hora da saida: " + movimentacao.getSaida() +
                "\n Placa do veiculo: " + movimentacao1.getVeiculo().getPlaca()+ "\n Modelo do veiculo: "+ movimentacao1.getVeiculo().getModelo().getNome()+
                "\n Ano do veiculo: "+ movimentacao1.getVeiculo().getAno()+"\n Cor do veiculo: "+ movimentacao1.getVeiculo().getCor()+
                "\n Tipo do veiculo: "+movimentacao1.getVeiculo().getTipo()
                +"\n Nome do condutor: "+ movimentacao1.getCondutor().getNome());

    }



}
