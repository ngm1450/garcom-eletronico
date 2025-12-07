package br.ge.backend.dto;

import lombok.Data;

@Data
public class OcupacaoDTO {
    private long totalMesas;
    private long mesasOcupadas;
    private double porcentagemOcupacao;
}