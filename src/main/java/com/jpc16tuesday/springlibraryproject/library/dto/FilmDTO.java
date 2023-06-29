package com.jpc16tuesday.springlibraryproject.library.dto;

import com.jpc16tuesday.springlibraryproject.library.model.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class FilmDTO
      extends GenericDTO {
    private String filmTitle;
    private LocalDate publishDate;
    private String publish;
    private Integer minutesCount;
    private Integer amount;
    private String IMDB;
    private String onlineCopyPath;
    private String description;
    private Genre genre;
    private List<Long> directorIds;
    private List<DirectorDTO> directorInfo;
}
