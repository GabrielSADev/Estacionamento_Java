package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Service
public class ConfiguracaoService {

    @Autowired
    ConfiguracaoRep configuracaoRep;

    static float PegaHoras;

    @Transactional(rollbackFor = Exception.class)
    public void valorHoraFunc(Configuracao configuracao){


        Configuracao configuracao1 = configuracaoRep.findByvalorHora(configuracao.getValorHora());

        PegaHoras = configuracao1.getValorHora();

        System.out.println(configuracao1.getValorHora());

    }


}
