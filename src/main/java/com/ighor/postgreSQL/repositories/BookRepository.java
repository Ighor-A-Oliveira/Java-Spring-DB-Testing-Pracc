package com.ighor.postgreSQL.repositories;

import com.ighor.postgreSQL.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//This makes this a bean that can be injected
//
@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
