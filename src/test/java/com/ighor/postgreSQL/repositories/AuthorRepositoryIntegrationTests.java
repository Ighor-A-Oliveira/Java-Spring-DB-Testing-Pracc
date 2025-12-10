
package com.ighor.postgreSQL.repositories;

import com.ighor.postgreSQL.TestDataUtil;
import com.ighor.postgreSQL.domain.Author;
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
public class AuthorRepositoryIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        //We create an author
        Author author = TestDataUtil.createTestAuthorA();
        // We save it the author, JPA assigns an id and we can reference that
        underTest.save(author);
        //We search based in id
        Optional<Author> result = underTest.findById(author.getId());
        //Then test it
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
        assertThat(result.get().getId()).isEqualTo(author.getId());
        assertThat(result.get().getName()).isEqualTo(author.getName());
        assertThat(result.get().getAge()).isEqualTo(author.getAge());
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        Author authorB = TestDataUtil.createTestAuthorB();
        underTest.save(authorB);
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorC);
        Iterable<Author> result = underTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorsCanBeUpdated(){
        //Created author
        Author authorA = TestDataUtil.createTestAuthorA();
        //Save author in db
        underTest.save(authorA);
        //Updated the name in the system
        authorA.setName("UPDATED");
        //Commited the update in the db
            //Save can be used to both create and update items in the db
        underTest.save(authorA);
        //Searched for the updated author
        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
        assertThat(result.get().getName()).isEqualTo("UPDATED");

    }

    @Test
    public void testThatAuthorsCanBeDeleted(){
        //Created author
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        //Delete the record from db
        underTest.deleteById(authorA.getId());
        //Searched for the updated author
        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isEmpty();

    }

    @Test //IMPORTANT CODE //REVIEW IT
    public void testThatGetAuthorsWithAgeLessThan(){
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        //ageLessThan() is a method that we created in the Author interface, but it has no implementation, Data JPA implements for us
            //Data JPA is smart enough to know that age is a var in the Author object and Less Than is a comparison, so it implements the method;
        Iterable<Author> result = underTest.ageLessThan(50);
        assertThat(result)
                .containsExactly(authorB, authorC);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan(){
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        //If the method name is too complex then Data JPA wont know what you want the implementation to be
        Iterable<Author> result = underTest.findAuthorsWithAgeGreaterThan(50);

        assertThat(result)
                .containsExactly(authorA);
    }

}

