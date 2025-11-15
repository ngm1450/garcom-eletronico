package br.ge.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gerentes")
@Getter
@Setter
@NoArgsConstructor
public class Gerente extends Usuario {

    @OneToOne(mappedBy = "gerente", cascade = CascadeType.ALL)
    private Cardapio cardapio;

}