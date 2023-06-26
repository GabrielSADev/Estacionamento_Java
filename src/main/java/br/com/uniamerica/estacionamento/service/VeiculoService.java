package br.com.uniamerica.estacionamento.service;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class VeiculoService {

    @Autowired
    VeiculoRep veiculoRep;

    @Transactional(rollbackFor = Exception.class)
    public void validaVeiculo(final Veiculo veiculo){

        Assert.isTrue(!
                veiculo.getPlaca().equals(""),"A placa não pode ser nula!");
        Assert.isTrue(veiculo.getPlaca().length() <= 15, "Máximo de caracteres alcançado");

        Assert.isTrue(!
                veiculo.getCor().equals(""),"A cor do veiculo nao pode ser nula!");

        Veiculo veiculoExistente = veiculoRep.findByPlaca(veiculo.getPlaca());
        Assert.isTrue(veiculoExistente == null || veiculoExistente.equals
                (veiculo.getPlaca()), "Placa já existente");

        veiculo.setAtivo(true);

        this.veiculoRep.save(veiculo);
    }

    public void atualizarVeiculo (Veiculo veiculo){
        final Veiculo veiculoAttService=this.veiculoRep.findById(veiculo.getId()).orElse(null);
        veiculo.setCadastro(veiculoAttService.getCadastro());

        this.veiculoRep.save(veiculo);

    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir(final Long id) {

        final Veiculo veiculoBanco = this.veiculoRep.findById(id).orElse(null);

        if (veiculoBanco == null || !veiculoBanco.getId().equals(id)){
            throw new RuntimeException("Não foi possivel identificar o modelo informado.");
        }

        this.veiculoRep.delete(veiculoBanco);
    }



}
