package br.ge.backend.controller;

import br.ge.backend.dto.ItemCardapioDTO;
import br.ge.backend.service.CardapioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CardapioController {

    private final CardapioService cardapioService;

    @GetMapping("/cardapio/buscar")
    public ResponseEntity<List<ItemCardapioDTO>> buscar(
            @RequestParam(required = false) String termo,
            @RequestParam(required = false) String semIngrediente) {

        List<ItemCardapioDTO> resultado = cardapioService.buscarItens(termo, semIngrediente);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/cardapio/destaques")
    public ResponseEntity<List<ItemCardapioDTO>> getDestaques() {
        List<ItemCardapioDTO> destaques = cardapioService.buscarDestaquesSemana();
        return ResponseEntity.ok(destaques);
    }

    @PatchMapping("/itens-cardapio/{id}/indisponibilizar")
    public ResponseEntity<String> indisponibilizarItem(@PathVariable Long id) {
        try {
            cardapioService.indisponibilizarItem(id);
            return ResponseEntity.ok("Item removido do cardápio ativo com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
