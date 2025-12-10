package com.ighor.postgreSQL.repositories;

import com.ighor.postgreSQL.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//This makes this a bean that can be injected
//
@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    Iterable<AuthorEntity> ageLessThan(int i);

    //SELECT a → you tell JPQL/HQL to return the entity instance represented by alias a
    //FROM Author a → the alias refers to Author and it maps the table authors
    //where a.age > ?1 → you use the alias again to reference its property (age)
    @Query("SELECT a FROM AuthorEntity a where a.age > ?1")
    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int i);
}