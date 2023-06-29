package com.jpc16tuesday.springlibraryproject.library.dto;

import com.jpc16tuesday.springlibraryproject.library.model.Director;
import com.jpc16tuesday.springlibraryproject.library.model.Film;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DirectorDTO extends GenericDTO {
    private String directorFIO;
    private LocalDate birthDate;
    private String description;
    private List<Long> filmsIds;


    public DirectorDTO(Director director) {
        this.birthDate = director.getBirthDate();
        this.createdBy = director.getCreatedBy();
        this.directorFIO = director.getDirectorFIO();
        this.description = director.getDescription();
        this.createdWhen = director.getCreatedWhen();
        this.id = director.getId();
        List<Film> films = director.getFilms();
        List<Long> filmIds = new ArrayList<>();
        films.forEach(b -> filmIds.add(b.getId()));
        this.filmsIds = filmIds;
        this.isDeleted = false;
    }
}
