package com.ighor.postgreSQL.services;

import com.ighor.postgreSQL.domain.entities.AuthorEntity;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);
}
