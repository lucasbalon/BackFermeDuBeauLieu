package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Substance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean active;

    public Substance(String name) {
        this.name = name;
        this.active = true;
    }

    public Substance() {

    }
}
