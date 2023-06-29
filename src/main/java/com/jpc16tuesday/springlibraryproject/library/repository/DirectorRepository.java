package com.jpc16tuesday.springlibraryproject.library.repository;

import com.jpc16tuesday.springlibraryproject.library.model.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository
        extends GenericRepository<Director> {
    Page<Director> findAllByDirectorFIOContainsIgnoreCaseAndIsDeletedFalse(String fio,
                                                                         Pageable pageable);


    @Query(value = """
          select case when count(a) > 0 then false else true end
          from Director a join a.films b
                        join FilmRentInfo bri on b.id = bri.film.id
          where a.id = :directorId
          and bri.returned = false
          """)
    boolean checkDirectorForDeletion(final Long directorId);
}
