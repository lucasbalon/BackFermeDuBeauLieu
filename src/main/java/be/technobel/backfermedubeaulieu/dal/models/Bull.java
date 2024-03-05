package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Bull extends Bovin{
    @OneToMany(mappedBy = "father")
    private List<Bovin> children;
}
