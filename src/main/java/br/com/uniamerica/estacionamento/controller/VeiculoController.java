package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRep;
import br.com.uniamerica.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new Veiculo());
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

    @PutMapping
    public ResponseEntity<?> editarVeiculo(@RequestParam("id") final Long id, @RequestBody final Veiculo veiculo){
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

    @DeleteMapping("delete/{id}")
    public void deletaVeiculo(@PathVariable Long id){
        veiculoRep.deleteById(id);
    }



}
