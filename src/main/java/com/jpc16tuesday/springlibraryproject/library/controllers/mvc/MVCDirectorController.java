package com.jpc16tuesday.springlibraryproject.library.controllers.mvc;


import com.jpc16tuesday.springlibraryproject.library.dto.AddFilmDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.DirectorDTO;
import com.jpc16tuesday.springlibraryproject.library.exception.MyDeleteException;
import com.jpc16tuesday.springlibraryproject.library.service.DirectorService;
import com.jpc16tuesday.springlibraryproject.library.service.FilmService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.jpc16tuesday.springlibraryproject.library.constants.UserRolesConstants.*;
@Controller
@Hidden
@RequestMapping("/directors")
@Slf4j
public class MVCDirectorController {
    private final DirectorService directorService;
    private final FilmService filmService;
    
    public MVCDirectorController(DirectorService directorService,
                                 FilmService filmService) {
        this.directorService = directorService;
        this.filmService = filmService;
    }
    
    @GetMapping("")
    public String getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int pageSize,
                         Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "directorFIO"));
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<DirectorDTO> result;
        if (ADMIN.equalsIgnoreCase(userName)) {
            result = directorService.listAll(pageRequest);
        }
        else {
            result = directorService.listAllNotDeleted(pageRequest);
        }
        model.addAttribute("directors", result);
        return "directors/viewAllDirectors";
    }
    
    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id,
                         Model model) {
        model.addAttribute("director", directorService.getOne(id));
        return "directors/viewDirector";
    }
    
    @GetMapping("/add")
    public String create() {
        return "directors/addDirector";
    }
    
    @PostMapping("/add")
    public String create(@ModelAttribute("directorForm") DirectorDTO directorDTO) {
        log.info(directorDTO.toString());
        directorService.create(directorDTO);
        return "redirect:/directors";
    }

    //@GetMapping("/update/{id}")
    @GetMapping("/update/{directorId}")
    public String update(@PathVariable("directorId") Long directorId,
                         Model model) {
        model.addAttribute("director", directorService.getOne(directorId));
        model.addAttribute("director_id", directorId);
        return "directors/updateDirector";
    }
    
    //@PostMapping("/update")
    @PostMapping("/update/{directorId}")
    public String update(@ModelAttribute("directorForm") DirectorDTO directorDTO,
                         @PathVariable("directorId") Long directorId) {

        //directorService.update(directorDTO);
        directorService.updateDirector(directorId, directorDTO);
        return "redirect:/directors";
    }
    
    @GetMapping("/add-film/{directorId}")
    public String addFilm(@PathVariable Long directorId,
                          Model model) {
        model.addAttribute("films", filmService.listAll());
        model.addAttribute("directorId", directorId);
        model.addAttribute("director", directorService.getOne(directorId).getDirectorFIO());
        return "directors/addDirectorFilm";
    }
    
    @PostMapping("/add-film")
    public String addFilm(@ModelAttribute("directorFilmForm") AddFilmDTO addFilmDTO) {
        directorService.addFilm(addFilmDTO);
        return "redirect:/directors";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        directorService.deleteSoft(id);
        return "redirect:/directors";
    }
    
    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        directorService.restore(id);
        return "redirect:/directors";
    }
    
    @PostMapping("/search")
    public String searchDirectors(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "5") int pageSize,
                                @ModelAttribute("directorSearchForm") DirectorDTO directorDTO,
                                Model model) {
        if (StringUtils.hasText(directorDTO.getDirectorFIO()) || StringUtils.hasLength(directorDTO.getDirectorFIO())) {
            PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "directorFIO"));
            model.addAttribute("directors", directorService.searchDirectors(directorDTO.getDirectorFIO().trim(), pageRequest));
            return "directors/viewAllDirectors";
        }
        else {
            return "redirect:/directors";
        }
    }
}
