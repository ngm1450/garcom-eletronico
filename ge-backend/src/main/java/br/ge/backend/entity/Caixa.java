package br.ge.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "caixas")
@Getter
@Setter
@NoArgsConstructor
public class Caixa extends Usuario {

    @ManyToMany(mappedBy = "caixas")
    private List<Conta> contasGerenciadas;

}