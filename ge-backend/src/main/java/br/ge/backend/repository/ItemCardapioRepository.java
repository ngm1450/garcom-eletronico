package br.ge.backend.repository;

import br.ge.backend.entity.ItemCardapio;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Long> {

    List<ItemCardapio> findByNomeContainingIgnoreCase(String nome);

    List<ItemCardapio> findByIngredientesNotContainingIgnoreCase(String ingrediente);

    List<ItemCardapio> findByNomeContainingIgnoreCaseOrIngredientesContainingIgnoreCase(String nome, String ingredientes);

    @Query("SELECT ip.itemCardapio " +
            "FROM ItemPedido ip " +
            "JOIN ip.pedido p " +
            "WHERE p.horarioPedido >= :dataInicio " +
            "GROUP BY ip.itemCardapio " +
            "ORDER BY SUM(ip.quantidade) DESC")
    List<ItemCardapio> findItensMaisVendidos(@Param("dataInicio") LocalDateTime dataInicio, Pageable pageable);

}