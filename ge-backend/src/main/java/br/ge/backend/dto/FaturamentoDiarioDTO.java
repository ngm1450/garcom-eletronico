package br.ge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FaturamentoDiarioDTO {
    private String tipoPagamento;
    private Double total;
}
