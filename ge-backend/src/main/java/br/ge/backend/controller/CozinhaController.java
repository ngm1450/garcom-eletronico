package br.ge.backend.controller;

import br.ge.backend.dto.PedidoCozinhaDTO;
import br.ge.backend.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CozinhaController {

    private final CozinhaService cozinhaService;

    @GetMapping("/cozinha/fila-producao")
    public ResponseEntity<List<PedidoCozinhaDTO>> getFilaProducao() {
        List<PedidoCozinhaDTO> fila = cozinhaService.buscarFilaProducao();
        return ResponseEntity.ok(fila);
    }

    @PatchMapping("/pedidos/{id}/iniciar-preparo")
    public ResponseEntity<String> iniciarPreparo(@PathVariable Long id) {
        try {
            cozinhaService.iniciarPreparo(id);
            return ResponseEntity.ok("Preparo iniciado. O tempo está correndo!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/pedidos/{id}/concluir-preparo")
    public ResponseEntity<String> concluirPreparo(@PathVariable Long id) {
        try {
            cozinhaService.concluirPreparo(id);
            return ResponseEntity.ok("Pedido pronto! Garçom notificado.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
