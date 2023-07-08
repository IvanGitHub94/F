package com.jpc16tuesday.springlibraryproject.library.controllers.mvc;

import com.jpc16tuesday.springlibraryproject.library.dto.DirectorDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.FeedbackDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.FilmDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.FilmSearchDTO;
import com.jpc16tuesday.springlibraryproject.library.exception.MyDeleteException;
import com.jpc16tuesday.springlibraryproject.library.service.FeedBackService;
import com.jpc16tuesday.springlibraryproject.library.service.FilmService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Controller
@RequestMapping("/feedbacks")
public class MVCFeedBackController {

    private final FeedBackService feedBackService;

    public MVCFeedBackController(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    /*@GetMapping
    public String getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int pageSize,
                         @ModelAttribute(name = "exception") final String exception,
                         Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "filmTitle"));
        Page<FilmDTO> films = filmService.getAllFilms(pageRequest);
        model.addAttribute("films", films);
        model.addAttribute("exception", exception);
        return "films/viewAllFilms";
    }*/


    @GetMapping("/{filmId}")
    public String create(Model model, @PathVariable("filmId") Long id) {
        model.addAttribute("film_id", id);
        return "feedbacks/addFeedBack";
    }

    //@PostMapping("/add")
    @PostMapping("/{filmId}")
    public String create(@ModelAttribute("addFeedbackForm")FeedbackDTO feedbackDTO,
                         @PathVariable("filmId") Long filmId, Authentication authentication) {
        log.info(feedbackDTO.toString());
        feedbackDTO.setFilmId(filmId);
        feedbackDTO.setNickname(authentication.getName());

        feedBackService.create(feedbackDTO);

        //return "redirect:/feedbacks";
        Long backPathId = feedbackDTO.getFilmId();
        //return "redirect:/films/1";
        return "redirect:/films/" + backPathId;
    }


    /*@GetMapping("/{id}")
    public String getOne(@PathVariable Long id,
                         Model model) {
        model.addAttribute("film", filmService.getOne(id));
        return "films/viewFilm";
    }*/

    /*@GetMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         Model model) {
        model.addAttribute("film", filmService.getOne(id));
        return "films/updateFilm";
    }*/

//    @PostMapping("/update")
//    public String update(@ModelAttribute("filmForm") FilmDTO filmDTO) {
//        filmService.update(filmDTO);
//        return "redirect:/films";
//    }

    /*@GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        filmService.deleteSoft(id);
        return "redirect:/films";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        filmService.restore(id);
        if (id.equals(123L)) {
            throw new NotFoundException("НЕ НАЙДЕНО!");
        }
        return "redirect:/films";
    }*/


    /*@PostMapping("/search")
    public String searchFilms(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int pageSize,
                              @ModelAttribute("filmSearchForm") FilmSearchDTO filmSearchDTO,
                              Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "title"));
        model.addAttribute("films", filmService.searchFilm(filmSearchDTO, pageRequest));
        return "films/viewAllFilms";
    }*/


    /*@PostMapping("/search/filmsByDirector")
    public String searchFilms(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int pageSize,
                              @ModelAttribute("directorSearchForm") DirectorDTO directorDTO,
                              Model model) {
        FilmSearchDTO filmSearchDTO = new FilmSearchDTO();
        filmSearchDTO.setDirectorFIO(directorDTO.getDirectorFIO());
        return searchFilms(page, pageSize, filmSearchDTO, model);
    }

    @GetMapping(value = "/download", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFilm(@Param(value = "filmId") Long filmId) throws IOException {
        FilmDTO filmDTO = filmService.getOne(filmId);
        Path path = Paths.get(filmDTO.getOnlineCopyPath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(createHeaders(path.getFileName().toString()))
                .contentLength(path.toFile().length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }*/

    private HttpHeaders createHeaders(final String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
        headers.add("Cache-Control", "no-cache, no-store");
        headers.add("Expires", "0");
        return headers;
    }

    @ExceptionHandler({MyDeleteException.class, AccessDeniedException.class, NotFoundException.class})
    public RedirectView handleError(HttpServletRequest request,
                                    Exception exception,
                                    RedirectAttributes redirectAttributes) {
        log.error("Запрос " + request.getRequestURL() + " вызвал ошибку: " + exception.getMessage());
        redirectAttributes.addFlashAttribute("exception", exception.getMessage());
        return new RedirectView("/feedbacks", true);
    }

}
