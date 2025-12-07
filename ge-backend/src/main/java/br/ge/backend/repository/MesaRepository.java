package br.ge.backend.repository;

import br.ge.backend.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    long countByDisponivel(boolean disponivel);
}
