package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
public class Substance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Substance(String name) {
        this.name = name;
    }

    public Substance() {

    }
}
