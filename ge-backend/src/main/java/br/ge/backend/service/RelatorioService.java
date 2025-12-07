package br.ge.backend.service;

import br.ge.backend.dto.*;
import br.ge.backend.enums.StatusPreparo;
import br.ge.backend.repository.RelatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final RelatorioRepository relatorioRepository;

    public List<FaturamentoDiarioDTO> getFaturamentoHoje() {
        LocalDateTime inicioDia = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime fimDia = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        return relatorioRepository.faturamentoPorTipo(inicioDia, fimDia);
    }

    public List<PerformanceGarcomDTO> getPerformanceGarcomMesAtual() {
        LocalDateTime inicioMes = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        LocalDateTime fimMes = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

        return relatorioRepository.performanceGarcom(inicioMes, fimMes);
    }

    public List<ItemRejeitadoDTO> getItensRejeitados() {
        return relatorioRepository.itensMaisRejeitados(StatusPreparo.CANCELADO);
    }

}
