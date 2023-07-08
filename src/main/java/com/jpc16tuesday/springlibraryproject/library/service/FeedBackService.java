package com.jpc16tuesday.springlibraryproject.library.service;

import com.jpc16tuesday.springlibraryproject.library.dto.FeedbackDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.FilmDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.FilmSearchDTO;
import com.jpc16tuesday.springlibraryproject.library.mapper.GenericMapper;
import com.jpc16tuesday.springlibraryproject.library.model.Feedback;
import com.jpc16tuesday.springlibraryproject.library.repository.DirectorRepository;
import com.jpc16tuesday.springlibraryproject.library.repository.FilmRepository;
import com.jpc16tuesday.springlibraryproject.library.repository.GenericRepository;
import com.jpc16tuesday.springlibraryproject.library.utils.FileHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class FeedBackService extends GenericService<Feedback, FeedbackDTO>{

    private final FilmRepository filmRepository;

    public FeedBackService(GenericRepository<Feedback> repository,
                           GenericMapper<Feedback, FeedbackDTO> mapper,
                           FilmRepository filmRepository) {
        super(repository, mapper);
        this.filmRepository = filmRepository;
    }

}
