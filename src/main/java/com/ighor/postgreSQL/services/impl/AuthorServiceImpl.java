package com.ighor.postgreSQL.services.impl;

import com.ighor.postgreSQL.domain.entities.AuthorEntity;
import com.ighor.postgreSQL.repositories.AuthorRepository;
import com.ighor.postgreSQL.services.AuthorService;
import org.springframework.stereotype.Service;

//Should only handle business logic, not HTTP stuff

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }


    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
//        Takes an AuthorEntity from the controller.
//        Applies business logic.
//        Calls the repository to persist the entity to the database.
        return authorRepository.save(authorEntity);
    }
}
