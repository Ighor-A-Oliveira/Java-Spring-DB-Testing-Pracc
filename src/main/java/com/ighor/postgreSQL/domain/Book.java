package com.ighor.postgreSQL.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "books")
public class Book {

    @Id
    private String isbn;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL) //CascadeType.ALL = When you do something to this entity, JPA will ALSO automatically perform the same operation on the associated entity.
    @JoinColumn(name = "author_id")
    private Author author;
}
