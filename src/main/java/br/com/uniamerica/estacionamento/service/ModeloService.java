package br.com.uniamerica.estacionamento.service;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.ModeloRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.beans.Transient;

@Service
public class ModeloService {

    @Autowired
    private ModeloRep modeloRep;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrarModelo(final Modelo modelo){
        Assert.isTrue(!modelo.getNome().equals(""),"Nome do modelo não pode ser nulo!");
        Assert.isTrue(modelo.getNome().length() <= 45, "Máximo de caracteres alcançado");

        Modelo modeloExistente = modeloRep.findByNome(modelo.getNome());
        Assert.isTrue(modeloExistente == null || modeloExistente.equals(modelo), "Modelo já existente");

        modelo.setAtivo(true);

        this.modeloRep.save(modelo);
    }

    public void atualizarModelo (Modelo modelo){
        final Modelo modeloAttService=this.modeloRep.findById(modelo.getId()).orElse(null);
        modelo.setCadastro(modeloAttService.getCadastro());

        this.modeloRep.save(modelo);

    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir(final Long id) {

        final Modelo modeloBanco = this.modeloRep.findById(id).orElse(null);

        if (modeloBanco == null || !modeloBanco.getId().equals(id)){
            throw new RuntimeException("Não foi possivel identificar o modelo informado.");
        }

        this.modeloRep.delete(modeloBanco);
    }

}
