package com.ighor.postgreSQL.mappers.impl;

import com.ighor.postgreSQL.domain.dto.AuthorDto;
import com.ighor.postgreSQL.domain.entities.AuthorEntity;
import com.ighor.postgreSQL.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

//Converts between DTO and Entity.

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {

    private ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override //Map from ENTITY to DTO
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override //Map from DTO to Entity
    public AuthorEntity mapfrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
