package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRep;
import br.com.uniamerica.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRep veiculoRep;

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id){
        final Veiculo veiculo = this.veiculoRep.findById(id).orElse(null);
        return ResponseEntity.ok(veiculo);
    }

    @GetMapping("/lista")
    public ResponseEntity <?> ListaVeiculo(){
        return ResponseEntity.ok(this.veiculoRep.findAll());

    }

    @GetMapping("/ativos/{ativo}")
    public ResponseEntity <?> ativo(@PathVariable("ativo") boolean ativo){
        if(!ativo){
            return ResponseEntity.ok(veiculoRep.findByAtivo(false));
        }
        return ResponseEntity.ok(veiculoRep.findByAtivo(true));
    }

    @PostMapping
    public ResponseEntity <?> cadastrarVeiculo(@RequestBody final Veiculo veiculo){
        try {
                veiculoService.validaVeiculo(veiculo);
            return ResponseEntity.ok("Veiculo cadastrado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarVeiculo(@PathVariable("id") final Long id, @RequestBody final Veiculo veiculo){
        try {
            veiculoService.atualizarVeiculo(veiculo);
            final Veiculo veiculo1 = this.veiculoRep.findById(id).orElse(null);

            if (veiculo1 == null || !veiculo1.getId().equals(veiculo.getId())){
                throw new RuntimeException("Nao foi possivel indentificar o registro informado");
            }
            return ResponseEntity.ok("Registro Cadastrado com Sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletaVeiculo(@PathVariable("id") final Long id){
        Optional<Veiculo> veiculoOptional = veiculoRep.findById(id);
        try {
            if (veiculoOptional.isPresent()){
                Veiculo veiculo = veiculoOptional.get();
                if (!veiculo.isAtivo()){
                    this.veiculoService.excluir(id);
                    return ResponseEntity.ok("Registro excluido com sucesso.");
                } else {
                    veiculo.setAtivo(false);
                    veiculoRep.save(veiculo);
                    return  ResponseEntity.ok("Desativado");
                }
            }
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
        return ResponseEntity.internalServerError().body("Algo deu errado");
    }



}
