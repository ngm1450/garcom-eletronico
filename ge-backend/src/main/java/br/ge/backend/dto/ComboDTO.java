package br.ge.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ComboDTO {
    private Long contaId;
    private Long clienteId;
    private List<Long> itensIds;
    private String nomeCombo;
    private double descontoPercentual;
}
