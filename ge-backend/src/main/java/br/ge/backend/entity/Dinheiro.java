package br.ge.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagamentos_dinheiro")
@NoArgsConstructor
public class Dinheiro extends Pagamento {}