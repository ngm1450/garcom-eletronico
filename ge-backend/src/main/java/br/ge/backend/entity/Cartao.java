package br.ge.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pagamentos_cartao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cartao extends Pagamento {

    @Column(name = "nro_transacao")
    private Integer nroTransacao;

}