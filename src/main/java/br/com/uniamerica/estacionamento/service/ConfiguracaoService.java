package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ConfiguracaoService {

    @Autowired
    ConfiguracaoRep configuracaoRep;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrarConfig(final Configuracao configuracao){
    }


}
