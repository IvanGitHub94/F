package com.jpc16tuesday.springlibraryproject.library.mapper;

import com.jpc16tuesday.springlibraryproject.library.dto.FilmRentInfoDTO;
import com.jpc16tuesday.springlibraryproject.library.model.FilmRentInfo;
import com.jpc16tuesday.springlibraryproject.library.repository.FilmRepository;
import com.jpc16tuesday.springlibraryproject.library.repository.UserRepository;
import com.jpc16tuesday.springlibraryproject.library.service.FilmService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.util.List;


@Component
public class FilmRentInfoMapper
        extends GenericMapper<FilmRentInfo, FilmRentInfoDTO> {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    private final FilmService filmService;

    protected FilmRentInfoMapper(ModelMapper mapper,
                                 FilmRepository filmRepository,
                                 UserRepository userRepository,
                                 FilmService filmService) {
        super(FilmRentInfo.class, FilmRentInfoDTO.class, mapper);
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
        this.filmService = filmService;
    }

    @PostConstruct
    public void setupMapper() {
        super.modelMapper.createTypeMap(FilmRentInfo.class, FilmRentInfoDTO.class)
                .addMappings(m -> m.skip(FilmRentInfoDTO::setUserId))
                .addMappings(m -> m.skip(FilmRentInfoDTO::setFilmId))
                .addMappings(m -> m.skip(FilmRentInfoDTO::setFilmDTO))
                .setPostConverter(toDTOConverter());

        super.modelMapper.createTypeMap(FilmRentInfoDTO.class, FilmRentInfo.class)
                .addMappings(m -> m.skip(FilmRentInfo::setUser))
                .addMappings(m -> m.skip(FilmRentInfo::setFilm))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(FilmRentInfoDTO source, FilmRentInfo destination) {
        destination.setFilm(filmRepository.findById(source.getFilmId()).orElseThrow(() -> new NotFoundException("Фильм не найден")));
        destination.setUser(userRepository.findById(source.getUserId()).orElseThrow(() -> new NotFoundException("Пользователь не найден")));
    }

    @Override
    protected void mapSpecificFields(FilmRentInfo source, FilmRentInfoDTO destination) {
        destination.setUserId(source.getUser().getId());
        destination.setFilmId(source.getFilm().getId());
        destination.setFilmDTO(filmService.getOne(source.getFilm().getId()));
    }

    @Override
    protected List<Long> getIds(FilmRentInfo entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}
