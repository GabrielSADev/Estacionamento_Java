package br.com.uniamerica.estacionamento.service;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.util.Assert;

@Service
public class MarcaService {

    @Autowired
    MarcaRep marcaRep;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrarMarca(final Marca marca){

        Assert.isTrue(!marca.getNome().equals(""),"Marca não pode ser nulo!");
        Assert.isTrue(marca.getNome().length() <= 50, "Máximo de caracteres alcançado");

        marca.setAtivo(true);

        this.marcaRep.save(marca);

    }

    public void atualizarMarca (Marca marca){
        final Marca marcaAttService=this.marcaRep.findById(marca.getId()).orElse(null);
        marca.setCadastro(marcaAttService.getCadastro());

        this.marcaRep.save(marca);

    }
}
