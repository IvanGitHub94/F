package com.jpc16tuesday.springlibraryproject.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "films")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Where(clause = "is_deleted=false")
@SequenceGenerator(name = "default_generator", sequenceName = "films_sequence", allocationSize = 1)
public class Film extends GenericModel {

    @Column(name = "title", nullable = false)
    private String filmTitle;

    @Column(name = "publish")
    private String publish;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "minutes_count")
    private Integer minutesCount;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    //@Column(name = "imdb", nullable = false)
    @Column(name = "imdb")
    private String IMDB;

    @Column(name = "online_copy_path")
    private String onlineCopyPath;

    @Column(name = "description")
    private String description;

    @Column(name = "genre", nullable = false)
    @Enumerated
    private Genre genre;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "films_directors",
            joinColumns = @JoinColumn(name = "film_id"), foreignKey = @ForeignKey(name = "FK_FILMS_DIRECTORS"),
            inverseJoinColumns = @JoinColumn(name = "director_id"), inverseForeignKey = @ForeignKey(name = "FK_DIRECTORS_FILMS"))
    List<Director> directors;

    @OneToMany(mappedBy = "film", cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    private List<FilmRentInfo> filmRentInfos;

    @OneToMany(mappedBy = "film"/*, cascade = {CascadeType.PERSIST, CascadeType.MERGE}*/)
    private List<Feedback> feedbacks;
}
