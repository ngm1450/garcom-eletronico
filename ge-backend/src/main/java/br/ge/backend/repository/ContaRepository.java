package br.ge.backend.repository;

import br.ge.backend.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    @Query("SELECT c FROM Conta c WHERE c.mesa.id = :mesaId AND c.pagamento IS NULL")
    Optional<Conta> findContaAbertaByMesaId(Long mesaId);
}