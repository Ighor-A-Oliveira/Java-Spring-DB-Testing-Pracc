
package com.ighor.postgreSQL.repositories;

import com.ighor.postgreSQL.TestDataUtil;
import com.ighor.postgreSQL.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorEntityRepositoryIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        //We create an author
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        // We save it the author, JPA assigns an id and we can reference that
        underTest.save(authorEntity);
        //We search based in id
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        //Then test it
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
        assertThat(result.get().getId()).isEqualTo(authorEntity.getId());
        assertThat(result.get().getName()).isEqualTo(authorEntity.getName());
        assertThat(result.get().getAge()).isEqualTo(authorEntity.getAge());
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        underTest.save(authorEntityA);
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        underTest.save(authorEntityB);
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
        underTest.save(authorEntityC);
        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorEntityA, authorEntityB, authorEntityC);
    }

    @Test
    public void testThatAuthorsCanBeUpdated(){
        //Created author
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        //Save author in db
        underTest.save(authorEntityA);
        //Updated the name in the system
        authorEntityA.setName("UPDATED");
        //Commited the update in the db
            //Save can be used to both create and update items in the db
        underTest.save(authorEntityA);
        //Searched for the updated author
        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntityA);
        assertThat(result.get().getName()).isEqualTo("UPDATED");

    }

    @Test
    public void testThatAuthorsCanBeDeleted(){
        //Created author
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        underTest.save(authorEntityA);
        //Delete the record from db
        underTest.deleteById(authorEntityA.getId());
        //Searched for the updated author
        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
        assertThat(result).isEmpty();

    }

    @Test //IMPORTANT CODE //REVIEW IT
    public void testThatGetAuthorsWithAgeLessThan(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);

        //ageLessThan() is a method that we created in the Author interface, but it has no implementation, Data JPA implements for us
            //Data JPA is smart enough to know that age is a var in the Author object and Less Than is a comparison, so it implements the method;
        Iterable<AuthorEntity> result = underTest.ageLessThan(50);
        assertThat(result)
                .containsExactly(authorEntityB, authorEntityC);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);

        //If the method name is too complex then Data JPA wont know what you want the implementation to be
        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(50);

        assertThat(result)
                .containsExactly(authorEntityA);
    }

}

