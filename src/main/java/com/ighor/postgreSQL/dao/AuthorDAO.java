package com.ighor.postgreSQL.dao;

import com.ighor.postgreSQL.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO {
    void create(Author author);

    Optional<Author> findOne(long authorId);

    List<Author> find();

    void update(Long id, Author author);

    void delete(Long id);
}
