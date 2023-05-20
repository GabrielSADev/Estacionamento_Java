package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRep;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRep movimentacaoRep;

    @Autowired
    private MovimentacaoService movimentacaoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoRep.findById(id).orElse(null);
        return ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/lista")
    public ResponseEntity <?> ListaMovimentacao(){
        return ResponseEntity.ok(this.movimentacaoRep.findAll());

    }

    @GetMapping("/abertos/{aberto}")
    public ResponseEntity <?> aberto(@PathVariable("aberto") boolean aberto){
        if(!aberto){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new Movimentacao());
    }

    @PostMapping
    public ResponseEntity <?> cadastrarMovimentacao(@RequestBody final Movimentacao movimentacao){
        try {
            movimentacaoService.verificarMovimentacao(movimentacao);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editarMovimentacao(@RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try {
            movimentacaoService.atualizarMovimentacao(movimentacao);
            final Movimentacao movimentacao1 = this.movimentacaoRep.findById(id).orElse(null);

            if (movimentacao1 == null || !movimentacao1.getId().equals(movimentacao1.getId())){
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
    public void deletaMovimentacao(@PathVariable Long id){
        movimentacaoRep.deleteById(id);
    }


}
