package com.ighor.postgreSQL.controllers;

import com.ighor.postgreSQL.domain.dto.AuthorDto;
import com.ighor.postgreSQL.domain.entities.AuthorEntity;
import com.ighor.postgreSQL.mappers.Mapper;
import com.ighor.postgreSQL.services.AuthorService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//Handles incoming HTTP requests

@RestController
public class AuthorController {

    private AuthorService authorService;
    //We are declaring a var with the interface, so we will receive a Mapper with AuthorEntity and AuthorDto
    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    //ResponseEntity is a Spring class that represents the full HTTP response
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
        // Convert DTO to Entity
        AuthorEntity authorEntity = authorMapper.mapfrom(author);
        // Call the service to handle business logic / persistence
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
        // Convert back to DTO for the response
            //HttpStatus.CREATED is the standard status code for successful POST requests that create a resource
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }
}
