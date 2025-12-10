
package com.ighor.postgreSQL.repositories;

import com.ighor.postgreSQL.TestDataUtil;
import com.ighor.postgreSQL.domain.entities.AuthorEntity;
import com.ighor.postgreSQL.domain.entities.BookEntity;
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
public class BookEntityRepositoryIntegrationTests {

    private BookRepository underTest;
    private AuthorRepository authorRepository;

    @Autowired
    public BookEntityRepositoryIntegrationTests(BookRepository underTest, AuthorRepository authorRepository) {
        this.underTest = underTest;
        this.authorRepository = authorRepository;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        //Create author
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        //Save author in order fo the id to be create then we save a reference
        //If we dont save the author for the id to be created then we break the flow of the test
        AuthorEntity saved =  authorRepository.save(authorEntity);
        //We use that reference to create the book
        BookEntity bookEntity = TestDataUtil.createTestBookA(saved);
        //Here we save the book
        underTest.save(bookEntity);
        //Then we try to find it by using the id
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        //Then we test it
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
        assertThat(result.get().getIsbn()).isEqualTo(bookEntity.getIsbn());
        assertThat(result.get().getTitle()).isEqualTo(bookEntity.getTitle());
        assertThat(result.get().getAuthorEntity()).isEqualTo(bookEntity.getAuthorEntity());

    }


    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        //Created the authors so we have the FKs
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        //Inserted Author in DB
        authorRepository.save(authorEntity);

        //Created the books and passed the author so the method can add the FK
        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorEntity);
        BookEntity bookEntityB = TestDataUtil.createTestBookB(authorEntity);
        BookEntity bookEntityC = TestDataUtil.createTestBookC(authorEntity);

        //Added the Fks
//        bookA.setAuthorId(author.getId());
//        bookB.setAuthorId(author.getId());
//        bookC.setAuthorId(author.getId());

        //Inserted Books in DB
        underTest.save(bookEntityA);
        underTest.save(bookEntityB);
        underTest.save(bookEntityC);

        //Started Find method
        Iterable<BookEntity> result = underTest.findAll();

        //Testing
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testThatBooksCanBeUpdated(){
        //Since book is dependent on author_id as FK we need to create it first
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        //Save author in the db
        authorRepository.save(authorEntity);

        //Created book
        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorEntity);

            //JPA sets the FK for us, so we dont need this line anymore
            //bookA.setAuthorId(author.getId());
        underTest.save(bookEntityA);

        //Updated the title in the system
        bookEntityA.setTitle("UPDATED");

        //Commited the update in the db
        underTest.save(bookEntityA);

        //Searched for the updated book
        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
        //Then we test it
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntityA);
        assertThat(result.get().getIsbn()).isEqualTo(bookEntityA.getIsbn());
        assertThat(result.get().getTitle()).isEqualTo("UPDATED");
        assertThat(result.get().getAuthorEntity()).isEqualTo(authorEntity);

    }

    @Test
    public void testThatBooksCanBeDeleted(){
        //Since book is dependent on author_id as FK we need to create it first
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        //Save it to the db
        authorRepository.save(authorEntity);

        //Created book
        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorEntity);

            //JPA sets the FK for us, so we dont need this line anymore
            //bookA.setAuthorId(author.getId());
        underTest.save(bookEntityA);


        //Commited the update in the db
        underTest.deleteById(bookEntityA.getIsbn());

        //Searched for the updated book
        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
        assertThat(result).isEmpty();

    }
}

