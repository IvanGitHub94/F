package com.jpc16tuesday.springlibraryproject.library.mapper;



import com.jpc16tuesday.springlibraryproject.library.dto.UserDTO;
import com.jpc16tuesday.springlibraryproject.library.model.GenericModel;
import com.jpc16tuesday.springlibraryproject.library.model.User;
import com.jpc16tuesday.springlibraryproject.library.repository.FilmRentInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper
      extends GenericMapper<User, UserDTO> {
    
    private final FilmRentInfoRepository filmRentInfoRepository;
    
    protected UserMapper(ModelMapper modelMapper,
                         FilmRentInfoRepository filmRentInfoRepository) {
        super(User.class, UserDTO.class, modelMapper);
        this.filmRentInfoRepository = filmRentInfoRepository;
    }
    
    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDTO.class)
              .addMappings(m -> m.skip(UserDTO::setUserFilmsRent)).setPostConverter(toDTOConverter());
        modelMapper.createTypeMap(UserDTO.class, User.class)
              .addMappings(m -> m.skip(User::setFilmRentInfos)).setPostConverter(toEntityConverter());
    }
    
    @Override
    protected void mapSpecificFields(UserDTO source, User destination) {
        if (!Objects.isNull(source.getUserFilmsRent())) {
            destination.setFilmRentInfos(filmRentInfoRepository.findAllById(source.getUserFilmsRent()));
        }
        else {
            destination.setFilmRentInfos(Collections.emptyList());
        }
    }
    
    @Override
    protected void mapSpecificFields(User source, UserDTO destination) {
        destination.setUserFilmsRent(getIds(source));
    }
    
    @Override
    protected List<Long> getIds(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getFilmRentInfos())
               ? null
               : entity.getFilmRentInfos().stream()
                     .map(GenericModel::getId)
                     .collect(Collectors.toList());
    }
}
