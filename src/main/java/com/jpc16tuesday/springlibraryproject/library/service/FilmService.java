package com.jpc16tuesday.springlibraryproject.library.service;


import com.jpc16tuesday.springlibraryproject.library.constants.Errors;
import com.jpc16tuesday.springlibraryproject.library.dto.FilmDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.FilmSearchDTO;
import com.jpc16tuesday.springlibraryproject.library.exception.MyDeleteException;
import com.jpc16tuesday.springlibraryproject.library.mapper.FilmMapper;
import com.jpc16tuesday.springlibraryproject.library.model.Director;
import com.jpc16tuesday.springlibraryproject.library.model.Film;
import com.jpc16tuesday.springlibraryproject.library.repository.DirectorRepository;
import com.jpc16tuesday.springlibraryproject.library.repository.FilmRepository;
import com.jpc16tuesday.springlibraryproject.library.utils.FileHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FilmService
        extends GenericService<Film, FilmDTO> {
    private final DirectorRepository directorRepository;

    protected FilmService(FilmRepository repository,
                          FilmMapper mapper,
                          DirectorRepository directorRepository) {
        super(repository, mapper);
        this.directorRepository = directorRepository;
    }


    public FilmDTO create(final FilmDTO newFilm,
                          MultipartFile file) {
        String fileName = FileHelper.createFile(file);
        newFilm.setOnlineCopyPath(fileName);
        newFilm.setCreatedWhen(LocalDateTime.now());
        newFilm.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return mapper.toDTO(repository.save(mapper.toEntity(newFilm)));
    }

    public Page<FilmDTO> getAllFilms(Pageable pageable) {
        Page<Film> filmsPaginated = repository.findAll(pageable); //TODO: Переделать отображение для admin, библиотекаря и для пользователя
        List<FilmDTO> result = mapper.toDTOs(filmsPaginated.getContent());
        return new PageImpl<>(result, pageable, filmsPaginated.getTotalElements());
    }

    public Page<FilmDTO> searchFilm(FilmSearchDTO filmSearchDTO,
                                    Pageable pageRequest) {

        String genre = filmSearchDTO.getGenre() != null
                ? String.valueOf(filmSearchDTO.getGenre().ordinal())
                : null;

        Page<Film> filmsPaginated = ((FilmRepository) repository).searchFilms(
                filmSearchDTO.getFilmTitle(),
                genre,
                filmSearchDTO.getDirectorFIO(),
                pageRequest
        );

        List<FilmDTO> result = mapper.toDTOs(filmsPaginated.getContent());
        return new PageImpl<>(result, pageRequest, filmsPaginated.getTotalElements());

    }

    public FilmDTO addDirector(final Long filmId,
                             final Long directorId) {
        FilmDTO film = getOne(filmId);
        Director director = directorRepository.findById(directorId).orElseThrow(() -> new NotFoundException("режиссер не найден"));
        film.getDirectorIds().add(director.getId());
        update(film);
        return film;
    }

    @Override
    public void deleteSoft(final Long id) throws MyDeleteException {
        Film film = repository.findById(id).orElseThrow(() -> new NotFoundException("Фильм не найден"));
        boolean filmCanBeDeleted = ((FilmRepository)repository).isFilmCanBeDeleted(id);
        if (filmCanBeDeleted) {
            markAsDeleted(film);
            repository.save(film);
        }
        else {
            throw new MyDeleteException(Errors.Film.FILM_DELETE_ERROR);
        }
    }
}
