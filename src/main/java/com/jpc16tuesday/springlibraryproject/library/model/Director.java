package com.jpc16tuesday.springlibraryproject.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "directors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "directors_sequence", allocationSize = 1)
//@ToString - может возникнуть циклическая зависимость
public class Director extends GenericModel {

    @Column(name = "fio", nullable = false)
    private String directorFIO;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    @JoinTable(name = "film_directors",
            joinColumns = @JoinColumn(name = "director_id"), foreignKey = @ForeignKey(name = "FK_DIRECTORS_FILMS"),
            inverseJoinColumns = @JoinColumn(name = "film_id"), inverseForeignKey = @ForeignKey(name = "FK_FILMS_DIRECTORS"))
    List<Film> films;
}
