package br.ge.backend.service;

import br.ge.backend.dto.OcupacaoDTO;
import br.ge.backend.entity.Conta;
import br.ge.backend.entity.Garcom;
import br.ge.backend.entity.Mesa;
import br.ge.backend.repository.ContaRepository;
import br.ge.backend.repository.GarcomRepository;
import br.ge.backend.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MesaService {

    private final MesaRepository mesaRepository;

    private final GarcomRepository garcomRepository;

    private final ContaRepository contaRepository;

    // POST /mesas/{id}/abrir
    @Transactional
    public void abrirMesa(Long mesaId, Long garcomId) {
        Mesa mesa = mesaRepository.findById(mesaId)
            .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));

        if (!mesa.isDisponivel()) {
            throw new RuntimeException("A mesa " + mesaId + " já está ocupada!");
        }

        Garcom garcom = garcomRepository.findById(garcomId)
            .orElseThrow(() -> new RuntimeException("Garçom não encontrado"));

        mesa.setDisponivel(false);
        mesa.setGarcom(garcom);
        mesaRepository.save(mesa);

        Conta novaConta = new Conta();
        novaConta.setMesa(mesa);
        novaConta.setNome("Mesa " + mesa.getNumero());

        contaRepository.save(novaConta);
    }

    // POST /mesas/{id}/trocar
    @Transactional
    public void trocarMesa(Long mesaAtualId, Long novaMesaId) {
        Mesa mesaAtual = mesaRepository.findById(mesaAtualId)
            .orElseThrow(() -> new RuntimeException("Mesa atual não encontrada"));

        Mesa novaMesa = mesaRepository.findById(novaMesaId)
                .orElseThrow(() -> new RuntimeException("Nova mesa não encontrada"));

        if (mesaAtual.isDisponivel()) {
            throw new RuntimeException("A mesa atual não está aberta para ser trocada.");
        }
        if (!novaMesa.isDisponivel()) {
            throw new RuntimeException("A nova mesa " + novaMesaId + " já está ocupada.");
        }

        Conta conta = contaRepository.findContaAbertaByMesaId(mesaAtualId)
            .orElseThrow(() -> new RuntimeException("Nenhuma conta aberta encontrada para esta mesa."));

        conta.setMesa(novaMesa);
        novaMesa.setGarcom(mesaAtual.getGarcom());

        mesaAtual.setDisponivel(true);
        mesaAtual.setGarcom(null);

        novaMesa.setDisponivel(false);

        mesaRepository.save(mesaAtual);
        mesaRepository.save(novaMesa);
        contaRepository.save(conta);
    }

    // POST /mesas/{id}/chamar-garcom
    public String chamarGarcom(Long mesaId) {
        Mesa mesa = mesaRepository.findById(mesaId)
            .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));

        Garcom garcom = mesa.getGarcom();

        if (garcom == null) {
            return "Nenhum garçom atendendo a mesa " + mesa.getNumero() + " no momento.";
        }

        System.out.println("LOG: Notificação enviada para " + garcom.getNome() + " ir à mesa " + mesa.getNumero());

        log.info("LOG: Notificação enviada para {} ir à mesa {} ", garcom.getNome(), mesa.getNumero());

        return "Garçom " + garcom.getNome() + " foi notificado!";
    }

    // GET /mesas/ocupacao-atual
    public OcupacaoDTO calcularOcupacao() {
        long total = mesaRepository.count();
        long livres = mesaRepository.countByDisponivel(true);
        long ocupadas = total - livres;

        double porcentagem = total > 0 ? ((double) ocupadas / total) * 100 : 0.0;

        OcupacaoDTO dto = new OcupacaoDTO();
        dto.setTotalMesas(total);
        dto.setMesasOcupadas(ocupadas);
        dto.setPorcentagemOcupacao(Math.round(porcentagem * 100.0) / 100.0);

        return dto;
    }
}
