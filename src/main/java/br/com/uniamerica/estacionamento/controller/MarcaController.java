package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRep;
import br.com.uniamerica.estacionamento.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/api/marca")
public class MarcaController {

    @Autowired
    private MarcaRep marcaRep;

    @Autowired
    private MarcaService marcaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id){
        final Marca marca = this.marcaRep.findById(id).orElse(null);
        return ResponseEntity.ok(marca);
    }
    @GetMapping("/lista")
    public ResponseEntity <?> ListaMarca(){
        return ResponseEntity.ok(this.marcaRep.findAll());
    }

    @GetMapping("/ativos/{ativo}")
    public ResponseEntity <?> ativo(@PathVariable("ativo") boolean ativo){
        if(!ativo){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new Marca());
    }

    @PostMapping
    public ResponseEntity <?> cadastrarMarca(@RequestBody final Marca marca){
        try {
            marcaService.cadastrarMarca(marca);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editarMarca(@RequestParam("id") final Long id, @RequestBody final Marca marca){
        try {
            marcaService.atualizarMarca(marca);
            final Marca marca1 = this.marcaRep.findById(id).orElse(null);

            if (marca1 == null || !marca1.getId().equals(marca.getId())){
                throw new RuntimeException("Nao foi possivel indentificar o registro informado");
            }
            return ResponseEntity.ok("Marca Cadastrada com Sucesso");
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
    public void deletaMarca(@PathVariable Long id){
        Optional<Marca> marcaOptional = marcaRep.findById(id);
        if (marcaOptional.isPresent()){
            Marca marca = marcaOptional.get();
            if (!marca.isAtivo()){
                marcaRep.deleteById(id);
            } else {
                marca.setAtivo(false);
                marcaRep.save(marca);
            }
        }
    }


}
