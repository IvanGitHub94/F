package com.jpc16tuesday.springlibraryproject.library.controllers.rest;


import com.jpc16tuesday.springlibraryproject.library.dto.FilmDTO;
import com.jpc16tuesday.springlibraryproject.library.model.Film;
import com.jpc16tuesday.springlibraryproject.library.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest/films")
@Tag(name = "Фильмы",
     description = "Контроллер для работы с фильмами сервиса")
public class FilmController
      extends GenericController<Film, FilmDTO> {
    public FilmController(FilmService filmService) {
        super(filmService);
    }
    
    @Operation(description = "Добавить фильм к режиссеру")
    @RequestMapping(value = "/addDirector", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilmDTO> addDirector(@RequestParam(value = "filmId") Long filmId,
                                             @RequestParam(value = "directorId") Long directorId) {
        
        return ResponseEntity.status(HttpStatus.OK).body(((FilmService) service).addDirector(filmId, directorId));
    }
}
