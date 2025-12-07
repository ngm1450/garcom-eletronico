package br.ge.backend.service;

import br.ge.backend.dto.*;
import br.ge.backend.entity.*;
import br.ge.backend.enums.StatusConta;
import br.ge.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final GerenteRepository gerenteRepository;
    private final PagamentoRepository pagamentoRepository;
    private final MesaRepository mesaRepository;

    public DetalheContaDTO calcularDetalhes(Long id) {
        Conta conta = buscarConta(id);

        double subtotal = 0.0;
        List<String> resumoItens = new ArrayList<>();

        if (conta.getPedidos() != null) {
            for (Pedido p : conta.getPedidos()) {
                for (ItemPedido ip : p.getItens()) {
                    double valorItem = ip.getQuantidade() * ip.getItemCardapio().getPreco();
                    subtotal += valorItem;
                    resumoItens.add(ip.getItemCardapio().getNome() + " (x" + ip.getQuantidade() + ") - R$ " + valorItem);
                }
            }
        }

        double taxaServico = subtotal * 0.10;
        double total = subtotal + taxaServico - conta.getDesconto();
        if (total < 0) total = 0.0;

        DetalheContaDTO dto = new DetalheContaDTO();
        dto.setIdConta(conta.getId());
        dto.setSubtotal(subtotal);
        dto.setTaxaServico(taxaServico);
        dto.setDesconto(conta.getDesconto());
        dto.setTotalFinal(total);
        dto.setStatus(conta.getStatus().toString());
        dto.setItensConsumidos(resumoItens);

        return dto;
    }

    @Transactional
    public void fecharConta(Long id) {
        Conta conta = buscarConta(id);

        if (conta.getStatus() != StatusConta.ABERTA) {
            throw new RuntimeException("A conta já está fechada ou paga.");
        }

        conta.setStatus(StatusConta.FECHADA);
        contaRepository.save(conta);
    }

    @Transactional
    public void aplicarDesconto(Long id, DescontoDTO dto) {
        boolean isGerente = gerenteRepository.existsById(dto.getGerenteId());
        if (!isGerente) {
            throw new RuntimeException("Apenas gerentes podem aplicar descontos.");
        }

        Conta conta = buscarConta(id);

        DetalheContaDTO valoresAtuais = calcularDetalhes(id);
        double valorDesconto = 0.0;

        if (dto.isPorcentagem()) {
            valorDesconto = valoresAtuais.getSubtotal() * (dto.getValor() / 100);
        } else {
            valorDesconto = dto.getValor();
        }

        conta.setDesconto(valorDesconto);
        contaRepository.save(conta);
    }

    @Transactional
    public void processarPagamento(Long id, PagamentoDTO dto) {
        Conta conta = buscarConta(id);
        DetalheContaDTO calculo = calcularDetalhes(id);

        if (dto.getValorPago() < calculo.getTotalFinal()) {
            throw new RuntimeException("Valor insuficiente. Total: R$ " + calculo.getTotalFinal());
        }

        Pagamento pagamento;

        switch (dto.getTipo().toUpperCase()) {
            case "DINHEIRO":
                pagamento = new Dinheiro();
                break;
            case "CARTAO":
                Cartao pCartao = new Cartao();
                pCartao.setNroTransacao(dto.getNumeroTransacao());
                pagamento = pCartao;
                break;
            case "CHEQUE":
                Cheque pCheque = new Cheque();
                pCheque.setNumero(dto.getNumeroCheque());
                pagamento = pCheque;
                break;
            default:
                throw new RuntimeException("Tipo de pagamento inválido.");
        }

        pagamento = pagamentoRepository.save(pagamento);

        conta.setPagamento(pagamento);
        conta.setStatus(StatusConta.PAGA);
        contaRepository.save(conta);

        if (conta.getMesa() != null) {
            Mesa mesa = conta.getMesa();
            mesa.setDisponivel(true);
            mesa.setGarcom(null);
            mesaRepository.save(mesa);
        }
    }

    private Conta buscarConta(Long id) {
        return contaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }
}