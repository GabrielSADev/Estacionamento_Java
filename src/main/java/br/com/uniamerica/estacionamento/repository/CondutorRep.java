package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface CondutorRep extends JpaRepository <Condutor,Long> {


    Condutor findByCpf(String cpf);

    List<Condutor> findByAtivo(boolean ativo);
}
