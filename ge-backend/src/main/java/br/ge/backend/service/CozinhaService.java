package br.ge.backend.service;

import br.ge.backend.dto.PedidoCozinhaDTO;
import br.ge.backend.entity.Pedido;
import br.ge.backend.enums.StatusPreparo;
import br.ge.backend.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CozinhaService {

    private final PedidoRepository pedidoRepository;

    public List<PedidoCozinhaDTO> buscarFilaProducao() {
        List<Pedido> pedidosPendentes = pedidoRepository.findAll().stream()
                .filter(p -> p.getStatus() != StatusPreparo.PRONTO)
                .toList();

        return pedidosPendentes.stream()
            .map(this::converterParaDTO).sorted(Comparator
                .comparing((PedidoCozinhaDTO p) -> p.getStatus() == StatusPreparo.EM_PREPARO ? 0 : 1)
                .thenComparing((PedidoCozinhaDTO p) -> p.isPrioridade() ? 0 : 1)
                .thenComparing(PedidoCozinhaDTO::getHorarioChegada)).collect(Collectors.toList());
    }

    @Transactional
    public void iniciarPreparo(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (pedido.getStatus() != StatusPreparo.PENDENTE) {
            throw new RuntimeException("Este pedido já foi iniciado ou concluído.");
        }

        pedido.setStatus(StatusPreparo.EM_PREPARO);
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void concluirPreparo(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (pedido.getStatus() != StatusPreparo.EM_PREPARO) {
            throw new RuntimeException("O pedido precisa ser iniciado antes de ser concluído.");
        }

        pedido.setStatus(StatusPreparo.PRONTO);
        pedido.setHorarioEntrega(LocalDateTime.now());

        pedidoRepository.save(pedido);

        notificarGarcom(pedido);
    }


    private PedidoCozinhaDTO converterParaDTO(Pedido p) {
        PedidoCozinhaDTO dto = new PedidoCozinhaDTO();
        dto.setIdPedido(p.getId());
        dto.setHorarioChegada(p.getHorarioPedido());
        dto.setStatus(p.getStatus());

        if (p.getConta() != null && p.getConta().getMesa() != null) {
            dto.setNumeroMesa(p.getConta().getMesa().getNumero());
            if (p.getConta().getMesa().getGarcom() != null) {
                dto.setNomeGarcom(p.getConta().getMesa().getGarcom().getNome());
            }
        }

        List<String> itens = p.getItens().stream()
                .map(item -> item.getQuantidade() + "x " + item.getItemCardapio().getNome())
                .collect(Collectors.toList());
        dto.setItensResumo(itens);

        boolean temBebida = p.getItens().stream()
                .anyMatch(i -> i.getItemCardapio().getCategoria().getNome().equalsIgnoreCase("Bebidas"));

        dto.setPrioridade(temBebida);

        return dto;
    }

    private void notificarGarcom(Pedido p) {
        String garcom = (p.getConta().getMesa().getGarcom() != null)
            ? p.getConta().getMesa().getGarcom().getNome()
            : "Garçom Geral";

        log.info("NOTIFICAÇÃO: {}, o pedido da Mesa {} está PRONTO!", garcom, p.getConta().getMesa().getNumero());

    }
}