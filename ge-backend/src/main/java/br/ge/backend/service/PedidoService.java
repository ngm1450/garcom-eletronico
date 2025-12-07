package br.ge.backend.service;

import br.ge.backend.dto.AdicionarItemDTO;
import br.ge.backend.dto.ComboDTO;
import br.ge.backend.entity.*;
import br.ge.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ItemCardapioRepository itemCardapioRepository;
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public Pedido adicionarItem(Long pedidoId, AdicionarItemDTO dto) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (pedido.getConta().getPagamento() != null) {
            throw new RuntimeException("Não é possível adicionar itens. A conta já está fechada/paga.");
        }

        ItemCardapio itemCardapio = itemCardapioRepository.findById(dto.getItemCardapioId())
            .orElseThrow(() -> new RuntimeException("Item de cardápio não encontrado"));

        if (!itemCardapio.isDisponivelNaCozinha()) {
            throw new RuntimeException("O item '" + itemCardapio.getNome() + "' está indisponível na cozinha no momento.");
        }

        ItemPedido novoItem = new ItemPedido();
        novoItem.setPedido(pedido);
        novoItem.setItemCardapio(itemCardapio);
        novoItem.setQuantidade(dto.getQuantidade());

        itemPedidoRepository.save(novoItem);

        return pedido;
    }

    @Transactional
    public void cancelarItem(Long itemPedidoId) {
        ItemPedido itemPedido = itemPedidoRepository.findById(itemPedidoId)
                .orElseThrow(() -> new RuntimeException("Item do pedido não encontrado"));

        LocalDateTime horaPedido = itemPedido.getPedido().getHorarioPedido();
        if (horaPedido.plusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Item já está em produção (tempo limite de cancelamento excedido).");
        }

        itemPedidoRepository.delete(itemPedido);
    }

    @Transactional
    public Pedido criarCombo(ComboDTO dto) {
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        Cliente cliente = null;
        if(dto.getClienteId() != null) {
            cliente = clienteRepository.findById(dto.getClienteId()).orElse(null);
        }

        Pedido pedidoCombo = new Pedido();
        pedidoCombo.setConta(conta);
        pedidoCombo.setCliente(cliente);
        pedidoCombo.setNumero(1234);
        pedidoCombo.setHorarioPedido(LocalDateTime.now());

        pedidoCombo = pedidoRepository.save(pedidoCombo);

        List<ItemPedido> itensDoCombo = new ArrayList<>();

        for (Long itemId : dto.getItensIds()) {
            ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item ID " + itemId + " não existe"));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedidoCombo);
            itemPedido.setItemCardapio(itemCardapio);
            itemPedido.setQuantidade(1.0f);

            itensDoCombo.add(itemPedido);
        }

        itemPedidoRepository.saveAll(itensDoCombo);

        return pedidoCombo;
    }
}
