package com.jpc16tuesday.springlibraryproject.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "film_rent_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "film_rent_info_seq", allocationSize = 1)
public class FilmRentInfo extends GenericModel {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_RENT_FILM_INFO_USER"))
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "film_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_RENT_FILM_INFO_FILM"))
    private Film film;

    @Column(name = "rent_date", nullable = false)
    private LocalDateTime rentDate;

    //поле автоматически должно рассчитываться из rent_date + rent_period
    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    //Количество дней аренды, по умолчанию 14
    @Column(name = "rent_period", nullable = false)
    private Integer rentPeriod;

    @Column(name = "returned", nullable = false)
    private Boolean returned;

}
