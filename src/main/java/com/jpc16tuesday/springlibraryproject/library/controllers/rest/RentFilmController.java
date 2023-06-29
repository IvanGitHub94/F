package com.jpc16tuesday.springlibraryproject.library.controllers.rest;


import com.jpc16tuesday.springlibraryproject.library.dto.FilmRentInfoDTO;
import com.jpc16tuesday.springlibraryproject.library.model.FilmRentInfo;
import com.jpc16tuesday.springlibraryproject.library.service.FilmRentInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest/rent/info")
@Tag(name = "Прокат фильмов",
     description = "Контроллер для работы с прокатом фильмов пользователям сервиса")
public class RentFilmController
      extends GenericController<FilmRentInfo, FilmRentInfoDTO> {
    
    public RentFilmController(FilmRentInfoService filmRentInfoService) {
        super(filmRentInfoService);
    }
}
