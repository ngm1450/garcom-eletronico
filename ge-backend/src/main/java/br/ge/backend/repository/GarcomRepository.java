package br.ge.backend.repository;

import br.ge.backend.entity.Garcom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarcomRepository extends JpaRepository<Garcom, Long> {}
