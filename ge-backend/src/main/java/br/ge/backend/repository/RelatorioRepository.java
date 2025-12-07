package br.ge.backend.repository;

import br.ge.backend.dto.FaturamentoDiarioDTO;
import br.ge.backend.dto.ItemRejeitadoDTO;
import br.ge.backend.dto.PerformanceGarcomDTO;
import br.ge.backend.entity.Pagamento;
import br.ge.backend.enums.StatusPreparo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Pagamento, Long> {

    @Query("SELECT new br.ge.backend.dto.FaturamentoDiarioDTO(TYPE(p).simpleName, SUM(p.valor)) " +
            "FROM Pagamento p " +
            "JOIN p.contas c " +
            "WHERE c.dataCriacao BETWEEN :inicio AND :fim " +
            "GROUP BY TYPE(p)")
    List<FaturamentoDiarioDTO> faturamentoPorTipo(@Param("inicio") LocalDateTime inicio,
                                                  @Param("fim") LocalDateTime fim);

    @Query("SELECT new br.ge.backend.dto.PerformanceGarcomDTO(g.nome, COUNT(DISTINCT c), SUM(p.valor)) " +
            "FROM Conta c " +
            "JOIN c.mesa m " +
            "JOIN m.garcom g " +
            "JOIN c.pagamento p " +
            "WHERE c.status = 'PAGA' " +
            "AND p IS NOT NULL " +
            "AND c.dataCriacao BETWEEN :inicio AND :fim " +
            "GROUP BY g.nome " +
            "ORDER BY SUM(p.valor) DESC")
    List<PerformanceGarcomDTO> performanceGarcom(@Param("inicio") LocalDateTime inicio,
                                                 @Param("fim") LocalDateTime fim);

    @Query("SELECT new br.ge.backend.dto.ItemRejeitadoDTO(ic.nome, COUNT(ip)) " +
            "FROM ItemPedido ip " +
            "JOIN ip.itemCardapio ic " +
            "WHERE ip.status = :statusCancelado " +
            "GROUP BY ic.nome " +
            "ORDER BY COUNT(ip) DESC")
    List<ItemRejeitadoDTO> itensMaisRejeitados(@Param("statusCancelado") StatusPreparo status);

}