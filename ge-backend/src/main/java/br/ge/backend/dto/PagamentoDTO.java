package br.ge.backend.dto;

import lombok.Data;

@Data
public class PagamentoDTO {
    private String tipo;
    private Double valorPago;
    private Integer numeroTransacao;
    private Integer numeroCheque;
}
