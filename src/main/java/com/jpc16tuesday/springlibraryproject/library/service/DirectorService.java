package com.jpc16tuesday.springlibraryproject.library.service;

import com.jpc16tuesday.springlibraryproject.library.constants.Errors;
import com.jpc16tuesday.springlibraryproject.library.dto.AddFilmDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.DirectorDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.FilmDTO;
import com.jpc16tuesday.springlibraryproject.library.exception.MyDeleteException;
import com.jpc16tuesday.springlibraryproject.library.mapper.DirectorMapper;
import com.jpc16tuesday.springlibraryproject.library.model.Director;
import com.jpc16tuesday.springlibraryproject.library.model.Film;
import com.jpc16tuesday.springlibraryproject.library.repository.DirectorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class DirectorService
        extends GenericService<Director, DirectorDTO> {

    public DirectorService(DirectorRepository directorRepository,
                           DirectorMapper directorMapper) {
        super(directorRepository, directorMapper);
    }

    public DirectorDTO addFilm(final AddFilmDTO addFilmDTO) {
        DirectorDTO director = getOne(addFilmDTO.getDirectorId());
        director.getFilmsIds().add(addFilmDTO.getFilmId());
        update(director);
        return director;
    }

    public DirectorDTO updateDirector(final Long directorId, DirectorDTO directorDTO) {
        DirectorDTO director = getOne(directorId);

        directorDTO.setFilmsIds(director.getFilmsIds());
        update(directorDTO);
        return directorDTO;
    }

    public Page<DirectorDTO> searchDirectors(final String fio,
                                           Pageable pageable) {
        Page<Director> directors = ((DirectorRepository)repository).findAllByDirectorFIOContainsIgnoreCaseAndIsDeletedFalse(fio, pageable);
        List<DirectorDTO> result = mapper.toDTOs(directors.getContent());
        return new PageImpl<>(result, pageable, directors.getTotalElements());
    }

    @Override
    public void deleteSoft(Long objectId) throws MyDeleteException {
        Director director = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Режиссера с заданным id=" + objectId + " не существует."));
        boolean directorCanBeDeleted = ((DirectorRepository)repository).checkDirectorForDeletion(objectId);
        if (directorCanBeDeleted) {
            markAsDeleted(director);
            List<Film> films = director.getFilms();
            if (films != null && films.size() > 0) {
                films.forEach(this::markAsDeleted);
            }
            ((DirectorRepository)repository).save(director);
        }
        else {
            throw new MyDeleteException(Errors.Directors.DIRECTOR_DELETE_ERROR);
        }
    }

    public void restore(Long objectId) {
        Director director = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Режиссера с заданным id=" + objectId + " не существует."));
        unMarkAsDeleted(director);
        List<Film> films = director.getFilms();
        if (films != null && films.size() > 0) {
            films.forEach(this::unMarkAsDeleted);
        }
        repository.save(director);
    }
}
