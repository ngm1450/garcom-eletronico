package br.ge.backend.dto;

import lombok.Data;

@Data
public class AdicionarItemDTO {
    private Long itemCardapioId;
    private Float quantidade;
    private String observacao;
}
