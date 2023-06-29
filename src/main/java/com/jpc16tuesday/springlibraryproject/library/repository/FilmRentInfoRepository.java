package com.jpc16tuesday.springlibraryproject.library.repository;

import com.jpc16tuesday.springlibraryproject.library.model.FilmRentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRentInfoRepository
      extends GenericRepository<FilmRentInfo> {
    Page<FilmRentInfo> getFilmRentInfoByUserId(Long id,
                                               Pageable pageRequest);
}
