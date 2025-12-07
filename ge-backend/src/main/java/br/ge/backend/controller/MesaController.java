package br.ge.backend.controller;

import br.ge.backend.dto.AbrirMesaDTO;
import br.ge.backend.dto.OcupacaoDTO;
import br.ge.backend.dto.TrocarMesaDTO;
import br.ge.backend.service.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mesas")
public class MesaController {

    private final MesaService mesaService;

    @PostMapping("/{id}/abrir")
    public ResponseEntity<String> abrirMesa(@PathVariable Long id, @RequestBody AbrirMesaDTO dto) {
        try {
            mesaService.abrirMesa(id, dto.getGarcomId());
            return ResponseEntity.ok("Mesa aberta com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/trocar")
    public ResponseEntity<String> trocarMesa(@PathVariable Long id, @RequestBody TrocarMesaDTO dto) {
        try {
            mesaService.trocarMesa(id, dto.getNovaMesaId());
            return ResponseEntity.ok("Troca de mesa realizada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/chamar-garcom")
    public ResponseEntity<String> chamarGarcom(@PathVariable Long id) {
        try {
            String mensagem = mesaService.chamarGarcom(id);
            return ResponseEntity.ok(mensagem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ocupacao-atual")
    public ResponseEntity<OcupacaoDTO> getOcupacao() {
        OcupacaoDTO stats = mesaService.calcularOcupacao();
        return ResponseEntity.ok(stats);
    }

}