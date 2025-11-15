package br.ge.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "garcons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Garcom extends Usuario {

    @OneToMany(mappedBy = "garcom")
    private List<Mesa> mesasAtendidas;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;
}