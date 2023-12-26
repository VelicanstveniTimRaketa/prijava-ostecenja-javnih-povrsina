package com.backend.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.aspectj.weaver.ast.Literal;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tipovi_ostecenja")
@RequiredArgsConstructor
public class TipOstecenja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long Id;

    @NonNull
    private String naziv;

    @JsonIgnore
    @NotNull
    @OneToMany(mappedBy = "tipOstecenja", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<GradskiUred> gradskiUredi;

    /*@NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "ostecenjeId")
    private TipOstecenja tipOstecenja;*/

    /*@JsonIgnore
    @OneToMany(mappedBy = "tipOstecenja", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Prijava> prijave;*/

}
