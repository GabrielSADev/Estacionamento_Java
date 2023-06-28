package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRep;
import br.com.uniamerica.estacionamento.service.ConfiguracaoService;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRep movimentacaoRep;

    @Autowired
    private MovimentacaoService movimentacaoService;

    @Autowired
    private ConfiguracaoService configuracaoService;

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
    public ResponseEntity <?> aberto(@PathVariable("aberto") boolean ativo){
        if(!ativo){
            return ResponseEntity.ok(movimentacaoRep.findByAtivo(false));
        }
        return ResponseEntity.ok(movimentacaoRep.findByAtivo(true));
    }

    @PostMapping
    public ResponseEntity <?> cadastrarMovimentacao(@RequestBody final Movimentacao movimentacao){
        try {
            movimentacaoService.verificarMovimentacao(movimentacao);
            return ResponseEntity.ok("Registro cadastrado com sucesso" + movimentacao.getEntrada());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarMovimentacao(@PathVariable("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try {
            final Movimentacao movimentacao1 = this.movimentacaoRep.findById(id).orElse(null);

            if (movimentacao1 == null || !movimentacao1.getId().equals(movimentacao.getId())){
                throw new RuntimeException("Nao foi possivel indentificar o registro informado");
            }
            return movimentacaoService.atualizarMovimentacao(movimentacao,id);

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
    public void deletaMovimentacao(@PathVariable Long id){

        Optional<Movimentacao> movimentacaoOptional = movimentacaoRep.findById(id);
        if (movimentacaoOptional.isPresent()){
            Movimentacao movimentacao = movimentacaoOptional.get();
            if (movimentacao.isAtivo()){
                movimentacao.setAtivo(false);
            }
        }
    }


}
