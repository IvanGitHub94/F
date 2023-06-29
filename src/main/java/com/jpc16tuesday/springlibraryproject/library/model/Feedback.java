package com.jpc16tuesday.springlibraryproject.library.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedbacks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "feedbacks_sequence", allocationSize = 1)
public class Feedback extends GenericModel {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_USERS_FEEDBACKS"))
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "film_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_FILMS_FEEDBACKS"))
    private Film film;

    @Column(name = "content")
    private String content;

    @Column(name = "rank")
    private Integer rank;


}
