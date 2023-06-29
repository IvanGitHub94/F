package com.jpc16tuesday.springlibraryproject.library.mapper;

import com.jpc16tuesday.springlibraryproject.library.dto.FeedbackDTO;
import com.jpc16tuesday.springlibraryproject.library.model.Feedback;
import com.jpc16tuesday.springlibraryproject.library.repository.FilmRepository;
import com.jpc16tuesday.springlibraryproject.library.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.util.List;

@Component
public class FeedbackMapper extends GenericMapper<Feedback, FeedbackDTO> {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    public FeedbackMapper(ModelMapper mapper,
                          FilmRepository filmRepository,
                          UserRepository userRepository) {
        super(Feedback.class, FeedbackDTO.class, mapper);
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
//        this.filmService = filmService;
    }


    @Override
    protected void mapSpecificFields(FeedbackDTO source, Feedback destination) {
        destination.setFilm(filmRepository.findById(source.getFilmId()).orElseThrow(() -> new NotFoundException("Фильм не найден")));
        destination.setUser(userRepository.findUserByLogin(source.getNickname()));
    }

    @Override
    protected void mapSpecificFields(Feedback source, FeedbackDTO destination) {
        destination.setNickname(source.getUser().getLogin());
        destination.setFilmId(source.getFilm().getId());
//        destination.setFilmDTO(filmService.getOne(source.getFilm().getId()));
    }

    @Override
    protected void setupMapper() {
        super.modelMapper.createTypeMap(Feedback.class, FeedbackDTO.class)
//                .addMappings(m -> m.skip(FeedbackDTO::setUserId))
                .addMappings(m -> m.skip(FeedbackDTO::setFilmId))
//                .addMappings(m -> m.skip(FeedbackDTO::setFilmDTO))
                .setPostConverter(toDTOConverter());

        super.modelMapper.createTypeMap(FeedbackDTO.class, Feedback.class)
                .addMappings(m -> m.skip(Feedback::setUser))
                .addMappings(m -> m.skip(Feedback::setFilm))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected List<Long> getIds(Feedback entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}
