package br.ge.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "contas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    @ManyToMany
    @JoinTable(
        name = "conta_caixa_gerencia",
        joinColumns = @JoinColumn(name = "conta_id"),
        inverseJoinColumns = @JoinColumn(name = "caixa_id")
    )
    private List<Caixa> caixas;

}