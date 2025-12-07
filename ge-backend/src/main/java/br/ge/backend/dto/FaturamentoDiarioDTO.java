package br.ge.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FaturamentoDiarioDTO {
    private String tipoPagamento;
    private Double total;

    public FaturamentoDiarioDTO(Object tipo, Double total) {
        if (tipo instanceof Class) {
            this.tipoPagamento = ((Class<?>) tipo).getSimpleName();
        } else {
            this.tipoPagamento = String.valueOf(tipo);
        }
        this.total = total;
    }
}