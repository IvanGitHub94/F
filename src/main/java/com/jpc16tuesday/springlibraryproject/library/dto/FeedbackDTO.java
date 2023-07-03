package com.jpc16tuesday.springlibraryproject.library.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class FeedbackDTO extends GenericDTO {

    private String nickname;
    private String content;
    private Integer rank;

    private Long filmId;

}
