package com.ighor.postgreSQL.domain.entities;

import jakarta.persistence.*;
import lombok.*;

//Getters * Seeters
@Data
//Constructors
@NoArgsConstructor
@AllArgsConstructor
//automatically generates the code for the Builder Design Pattern, very useful
@Builder
//Labels this object as an entity that can be used by Spring Data JPA
@Entity
//Since this is a high level abstraction we are gonna map each table to an entity
@Table(name = "authors")
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // 👈 important
public class AuthorEntity {


    //@EqualsAndHashCode.Include // 👈 equality based only on id
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq") //Issue with test: since in the test we add an id by hand the sequence breaks
    private Long id;
    private String name;
    private Integer age;
}
