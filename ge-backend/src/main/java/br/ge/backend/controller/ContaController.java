package br.ge.backend.controller;

import br.ge.backend.dto.DescontoDTO;
import br.ge.backend.dto.DetalheContaDTO;
import br.ge.backend.dto.PagamentoDTO;
import br.ge.backend.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contas")
public class ContaController {

    private final ContaService contaService;

    @GetMapping("/{id}/detalhe-consumo")
    public ResponseEntity<DetalheContaDTO> verDetalhes(@PathVariable Long id) {
        try {
            DetalheContaDTO detalhe = contaService.calcularDetalhes(id);
            return ResponseEntity.ok(detalhe);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/fechar")
    public ResponseEntity<String> fecharConta(@PathVariable Long id) {
        try {
            contaService.fecharConta(id);
            return ResponseEntity.ok("Conta fechada. Aguardando pagamento.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/aplicar-desconto")
    public ResponseEntity<String> aplicarDesconto(@PathVariable Long id, @RequestBody DescontoDTO dto) {
        try {
            contaService.aplicarDesconto(id, dto);
            return ResponseEntity.ok("Desconto aplicado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<String> pagarConta(@PathVariable Long id, @RequestBody PagamentoDTO dto) {
        try {
            contaService.processarPagamento(id, dto);
            return ResponseEntity.ok("Pagamento realizado! Mesa liberada.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}