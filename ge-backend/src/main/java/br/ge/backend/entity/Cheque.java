package br.ge.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pagamentos_cheque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cheque extends Pagamento {

    private Integer numero;

}