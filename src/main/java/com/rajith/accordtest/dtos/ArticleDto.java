package com.rajith.accordtest.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rajith.accordtest.entilty.Author;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ArticleDto implements Serializable {

    private static final long serialVersionUID = 3995177381014001926L;

    private Long id;
    private String title;
    private String content;
    private AuthorDto author;

}
