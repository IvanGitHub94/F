package com.jpc16tuesday.springlibraryproject.library.model;

public enum Genre {
    FANTASY("Фэнтэзи"),
    SCIENCE_FICTION("Фантастика"),
    DRAMA("Драма"),
    NOVEL("Новелла"),
    ACTION("Боевик"),
    COMEDY("Комедия"),
    BIOGRAPHY("Биографический"),
    DOCUMENTARY("Документальный"),
    THRILLER("Триллер"),
    HORROR("Ужасы"),
    ADVENTURE("Приключения"),
    MELODRAMA("Мелодрама");

    private final String genreTextDisplay;

    Genre(String text) {
        this.genreTextDisplay = text;
    }

    public String getGenreTextDisplay() {
        return this.genreTextDisplay;
    }
}
