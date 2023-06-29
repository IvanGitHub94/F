package com.jpc16tuesday.springlibraryproject.library.repository;

import com.jpc16tuesday.springlibraryproject.library.model.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository
        extends GenericRepository<Film> {

    @Query(nativeQuery = true,
            value = """
                       select distinct b.*
                       from films b
                                left join films_directors ba on b.id = ba.film_id
                                left join directors a on a.id = ba.director_id
                       where b.title ilike '%' || coalesce(:title, '%')  || '%'
                         and cast(b.genre as char) like coalesce(:genre, '%')
                         and coalesce(a.fio, '%') ilike '%' ||  coalesce(:fio, '%')  || '%'
                         and b.is_deleted = false
                    """)
    Page<Film> searchFilms(@Param(value = "title") String filmTitle,
                           @Param(value = "genre") String genre,
                           @Param(value = "fio") String directorFIO,
                           Pageable pageRequest);

    @Query("""
          select case when count(b) > 0 then false else true end
          from Film b join FilmRentInfo bri on b.id = bri.film.id
          where b.id = :id and bri.returned = false
          """)
    boolean isFilmCanBeDeleted(final Long id);
}
