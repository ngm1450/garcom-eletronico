package br.ge.backend.dto;

import lombok.Data;

@Data
public class DescontoDTO {
    private Long gerenteId;
    private Double valor;
    private boolean isPorcentagem;
}