package br.ge.backend.controller;

import br.ge.backend.dto.*;
import br.ge.backend.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/faturamento-diario")
    public ResponseEntity<List<FaturamentoDiarioDTO>> faturamentoDiario() {
        return ResponseEntity.ok(relatorioService.getFaturamentoHoje());
    }

    @GetMapping("/garcom-performance")
    public ResponseEntity<List<PerformanceGarcomDTO>> performanceGarcom() {
        return ResponseEntity.ok(relatorioService.getPerformanceGarcomMesAtual());
    }

    @GetMapping("/itens-rejeitados")
    public ResponseEntity<List<ItemRejeitadoDTO>> itensRejeitados() {
        return ResponseEntity.ok(relatorioService.getItensRejeitados());
    }

}