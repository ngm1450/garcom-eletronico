package br.ge.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class DetalheContaDTO {
    private Long idConta;
    private Double subtotal;
    private Double taxaServico;
    private Double desconto;
    private Double totalFinal;
    private String status;
    private List<String> itensConsumidos;
}
