package com.jpc16tuesday.springlibraryproject.library.controllers.rest;

import com.jpc16tuesday.springlibraryproject.library.dto.AddFilmDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.DirectorDTO;
import com.jpc16tuesday.springlibraryproject.library.model.Director;
import com.jpc16tuesday.springlibraryproject.library.service.DirectorService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Hidden
@RestController
@RequestMapping("/api/rest/directors") // http://localhost:8080/directors
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Режиссеры", description = "Контроллер для работы с режиссерами фильмов из фильмотеки")
public class DirectorController
        extends GenericController<Director, DirectorDTO> {

    public DirectorController(DirectorService directorService) {
        super(directorService);
    }

    @Operation(description = "Добавить книгу к автору")
    @RequestMapping(value = "/addFilm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectorDTO> addFilm(@RequestParam(value = "filmId") Long filmId,
                                               @RequestParam(value = "directorId") Long directorId) {
        AddFilmDTO addFilmDTO = new AddFilmDTO();
        addFilmDTO.setDirectorId(directorId);
        addFilmDTO.setFilmId(filmId);
        return ResponseEntity.status(HttpStatus.OK).body(((DirectorService) service).addFilm(addFilmDTO));
    }


}
