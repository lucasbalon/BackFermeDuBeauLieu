package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@DiscriminatorValue("COW")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class Cow extends Bull {
    @OneToMany(mappedBy = "cow")
    private List<Scan> scans;
}