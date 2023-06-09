package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ModeloRep;
import br.com.uniamerica.estacionamento.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloController {

    @Autowired
    private ModeloRep modeloRep;

    @Autowired
    private ModeloService modeloService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id){
        final Modelo modelo = this.modeloRep.findById(id).orElse(null);
        return ResponseEntity.ok(modelo);
    }


    @GetMapping("/lista")
    public ResponseEntity <?> ListaCompleta(){
        return ResponseEntity.ok(this.modeloRep.findAll());
    }

    @GetMapping("/ativos/{ativo}")
    public ResponseEntity <?> ativoModelo(@PathVariable("ativo") boolean ativo){
        if(!ativo){
            return ResponseEntity.ok(modeloRep.findByAtivo(false));
        }
        return ResponseEntity.ok(modeloRep.findByAtivo(true));
    }


    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Modelo modelo){
       try {
           modeloService.cadastrarModelo(modelo);
           return ResponseEntity.ok("Registro cadastrado com sucesso");
       }
       catch (Exception e){
           return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
       }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id, @RequestBody final Modelo modelo){
        try {
            modeloService.atualizarModelo(modelo);
            final Modelo modelo1 = this.modeloRep.findById(id).orElse(null);

            if (modelo1 == null || !modelo1.getId().equals(modelo.getId())){
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
    public ResponseEntity<?> deletaModelo(@PathVariable("id") final Long id){
        Optional<Modelo> modeloOptional = modeloRep.findById(id);
        try {
            if (modeloOptional.isPresent()){
                Modelo modelo = modeloOptional.get();
                if (!modelo.isAtivo()){
                    this.modeloService.excluir(id);
                    return ResponseEntity.ok("Registro excluido com sucesso.");
                } else {
                    modelo.setAtivo(false);
                    modeloRep.save(modelo);
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
