package br.ge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemRejeitadoDTO {
    private String nomeItem;
    private Long quantidadeCancelada;
}
