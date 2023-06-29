package com.jpc16tuesday.springlibraryproject.library.service;


import com.jpc16tuesday.springlibraryproject.library.dto.FilmDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.FilmRentInfoDTO;
import com.jpc16tuesday.springlibraryproject.library.mapper.FilmRentInfoMapper;
import com.jpc16tuesday.springlibraryproject.library.model.FilmRentInfo;
import com.jpc16tuesday.springlibraryproject.library.repository.FilmRentInfoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FilmRentInfoService
        extends GenericService<FilmRentInfo, FilmRentInfoDTO> {
    private final FilmService filmService;

    protected FilmRentInfoService(FilmRentInfoRepository filmRentInfoRepository,
                                  FilmRentInfoMapper filmRentInfoMapper,
                                  FilmService filmService) {
        super(filmRentInfoRepository, filmRentInfoMapper);
        this.filmService = filmService;
    }

    public FilmRentInfoDTO rentFilm(final FilmRentInfoDTO rentFilmInfoDTO) {
        FilmDTO filmDTO = filmService.getOne(rentFilmInfoDTO.getFilmId());
        filmDTO.setAmount(filmDTO.getAmount() - 1);
        filmService.update(filmDTO);
        long rentPeriod = rentFilmInfoDTO.getRentPeriod() != null ? rentFilmInfoDTO.getRentPeriod() : 14L;
        rentFilmInfoDTO.setRentDate(LocalDateTime.now());
        rentFilmInfoDTO.setReturned(false);
        rentFilmInfoDTO.setRentPeriod((int) rentPeriod);
        rentFilmInfoDTO.setReturnDate(LocalDateTime.now().plusDays(rentPeriod));
        rentFilmInfoDTO.setCreatedWhen(LocalDateTime.now());
        rentFilmInfoDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return mapper.toDTO(repository.save(mapper.toEntity(rentFilmInfoDTO)));
    }

    public Page<FilmRentInfoDTO> listUserRentFilms(final Long id,
                                                   final Pageable pageRequest) {
        Page<FilmRentInfo> objects = ((FilmRentInfoRepository) repository).getFilmRentInfoByUserId(id, pageRequest);
        List<FilmRentInfoDTO> results = mapper.toDTOs(objects.getContent());
        return new PageImpl<>(results, pageRequest, objects.getTotalElements());
    }

    public void returnFilm(final Long id) {
        FilmRentInfoDTO filmRentInfoDTO = getOne(id);
        filmRentInfoDTO.setReturned(true);
        filmRentInfoDTO.setReturnDate(LocalDateTime.now());
        FilmDTO filmDTO = filmRentInfoDTO.getFilmDTO();
        filmDTO.setAmount(filmDTO.getAmount() + 1);
        update(filmRentInfoDTO);
        filmService.update(filmDTO);
    }

}
