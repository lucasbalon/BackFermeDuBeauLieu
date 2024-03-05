package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Pasture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double size;
    @OneToMany(mappedBy = "pasture")
    private List<Bovin> bovin;
}
