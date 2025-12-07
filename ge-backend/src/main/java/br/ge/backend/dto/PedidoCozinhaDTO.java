package br.ge.backend.dto;

import br.ge.backend.enums.StatusPreparo;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoCozinhaDTO {
    private Long idPedido;
    private Integer numeroMesa;
    private String nomeGarcom;
    private List<String> itensResumo;
    private LocalDateTime horarioChegada;
    private StatusPreparo status;
    private boolean isPrioridade;
}