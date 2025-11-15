package br.ge.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "cozinhas")
@Getter
@Setter
@NoArgsConstructor
public class Cozinha extends Usuario {

    @OneToMany(mappedBy = "cozinha")
    private List<Pedido> pedidos;

}