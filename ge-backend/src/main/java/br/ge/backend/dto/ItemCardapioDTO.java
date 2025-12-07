package br.ge.backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemCardapioDTO {
    private Long id;
    private String nome;
    private String ingredientes;
    private Float preco;
    private boolean disponivel;
    private String categoriaNome;
}