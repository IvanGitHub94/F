package com.jpc16tuesday.springlibraryproject.library.model;

public enum Genre { // при добавлении в БД id начинаются с 0 (Фэнтэзи - id 0, Фантастика - 1, и т.д.)
    FANTASY("Фэнтэзи"), // 0
    SCIENCE_FICTION("Фантастика"), // 1
    DRAMA("Драма"), // 2
    NOVEL("Новелла"), // 3
    ACTION("Боевик"), // 4
    COMEDY("Комедия"), // 5
    BIOGRAPHY("Биографический"), // 6
    DOCUMENTARY("Документальный"), // 7
    THRILLER("Триллер"), // 8
    HORROR("Ужасы"), // 9
    ADVENTURE("Приключения"), // 10
    MELODRAMA("Мелодрама"); // 11

    private final String genreTextDisplay;

    Genre(String text) {
        this.genreTextDisplay = text;
    }

    public String getGenreTextDisplay() {
        return this.genreTextDisplay;
    }
}
