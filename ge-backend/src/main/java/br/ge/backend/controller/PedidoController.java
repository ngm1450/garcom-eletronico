package br.ge.backend.controller;

import br.ge.backend.dto.AdicionarItemDTO;
import br.ge.backend.dto.ComboDTO;
import br.ge.backend.entity.Pedido;
import br.ge.backend.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping("/{id}/adicionar-item")
    public ResponseEntity<?> adicionarItem(@PathVariable Long id, @RequestBody AdicionarItemDTO dto) {
        try {
            Pedido pedidoAtualizado = pedidoService.adicionarItem(id, dto);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/itens/{itemId}/cancelar")
    public ResponseEntity<String> cancelarItem(@PathVariable Long itemId) {
        try {
            pedidoService.cancelarItem(itemId);
            return ResponseEntity.ok("Item cancelado e removido do pedido.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/combo")
    public ResponseEntity<?> criarCombo(@RequestBody ComboDTO dto) {
        try {
            Pedido pedidoCombo = pedidoService.criarCombo(dto);
            return ResponseEntity.ok(pedidoCombo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}