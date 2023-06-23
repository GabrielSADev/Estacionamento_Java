package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRep;
import br.com.uniamerica.estacionamento.service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/configuracao")
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoRep configuracaoRep;

    @Autowired
    private ConfiguracaoService configuracaoService;

    @GetMapping("/{id}")
    public ResponseEntity <?> findByIdPath(@PathVariable("id") final Long id){
        final Configuracao configuracao = this.configuracaoRep.findById(id).orElse(null);
        return ResponseEntity.ok(configuracao);
    }


    @PostMapping
    public ResponseEntity <?> cadastConfig(@RequestBody final Configuracao configuracao){
        try {
            this.configuracaoRep.save(configuracao);
            this.configuracaoService.valorHoraFunc(configuracao);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editarConfig(@RequestParam("id") final Long id, @RequestBody final Configuracao configuracao){
        try {
            final Configuracao configuracao1 = this.configuracaoRep.findById(id).orElse(null);

            if (configuracao1 == null || configuracao1.getId().equals(configuracao.getId())){
                throw new RuntimeException("Nao foi possivel indentificar o registro informado");
            }
            this.configuracaoRep.save(configuracao);
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


}
