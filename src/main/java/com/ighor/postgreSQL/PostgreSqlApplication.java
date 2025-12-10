package com.ighor.postgreSQL;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class PostgreSqlApplication /*implements CommandLineRunner*/ {

    /*private final DataSource dataSource;

    public PostgreSqlApplication(final DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

	public static void main(String[] args) {
		SpringApplication.run(PostgreSqlApplication.class, args);
	}

    /*@Override
    public void run(final String... args){
        log.info("Datasource: "+dataSource.toString());
        final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
        restTemplate.execute("select 1");

    }*/

}

//Proccess
//Client sends AuthorDto to your API, since we only expose data via DTO
//
//Controller uses mapper to convert DTO → Entity.
//
//Controller calls service to persist entity.
//
//Service calls repository to save entity in DB.
//
//Service returns saved entity to controller.
//
//Controller uses mapper to convert Entity → DTO.
//
//Controller returns DTO to client.