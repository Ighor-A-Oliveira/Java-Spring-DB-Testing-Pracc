package com.ighor.postgreSQL.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto {

    //This class is used to distance our business logic from the presentation layer

    private Long id;
    private String name;
    private Integer age;
}
