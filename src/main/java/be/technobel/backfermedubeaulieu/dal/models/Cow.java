package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@DiscriminatorValue("COW")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Cow extends Bull{
    @OneToMany(mappedBy = "cow")
    private List<Scan> scans;
}

