package br.com.uniamerica.estacionamento.service;
import br.com.uniamerica.estacionamento.configs.ValidaCPF;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class CondutorService {

    @Autowired
    CondutorRep condutorRep;
    @Autowired
    private ValidaCPF validaCPF;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Condutor condutor) {

        Assert.isTrue(!condutor.getNome().equals(""), "Nome não pode ser nulo!!");
        Assert.isTrue(condutor.getNome().length() <= 50, "Máximo de caracteres alcançado");
        Assert.isTrue(!condutor.getCpf().equals(""), "CPF não pode ser nulo!!");
        Assert.isTrue(condutor.getCpf().length() == 11, "CPF deve conter 11 digitos");
        Condutor condutorExistente = condutorRep.findByCpf(condutor.getCpf());
        Assert.isTrue(condutorExistente == null || condutorExistente.equals
                (condutor.getCpf()), "Cpf já existente");

        if (this.validaCPF.isCPF(condutor.getCpf()) == true) {
            System.out.printf("%s\n", this.validaCPF.imprimeCPF(condutor.getCpf()));
        }
        else {
            int x = 2;
            System.out.printf("Erro, CPF invalido !!!\n");
            Assert.isTrue(x == 1, "Cpf inválido");

        }

        Assert.isTrue(condutor.getTelefone().substring(0,11).matches("[0-9]*"),"Telefone deve conter apenas números!");
        Assert.isTrue(!condutor.getTelefone().equals(""), "Telefone não pode ser nulo!");
        Assert.isTrue(condutor.getTelefone().length() == 11, "Telefone deve conter 11 digítos");

        condutor.setAtivo(true);

        this.condutorRep.save(condutor);

    }

    public void atualizaCondutor (Condutor condutor){
        final Condutor condutorAttService=this.condutorRep.findById(condutor.getId()).orElse(null);

        Assert.isTrue(!condutor.getNome().equals(""),"Nome não pode ser nulo!");
        Assert.isTrue(condutor.getNome().length() <= 50,"Máximo de 50 caracteres");

        Assert.isTrue(!condutor.getCpf().equals(""),"Cpf não pode ser nulo");
        Assert.isTrue(condutor.getCpf().length() == 11, "Cpf deve conter 11 digitos");

        condutor.setCadastro(condutorAttService.getCadastro());

        this.condutorRep.save(condutor);

    }

}


