package br.ge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerformanceGarcomDTO {
    private String nomeGarcom;
    private Long mesasAtendidas;
    private Double faturamentoGerado;
}
