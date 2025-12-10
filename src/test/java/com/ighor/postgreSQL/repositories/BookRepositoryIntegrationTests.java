
package com.ighor.postgreSQL.repositories;

import com.ighor.postgreSQL.TestDataUtil;
import com.ighor.postgreSQL.domain.Author;
import com.ighor.postgreSQL.domain.Book;
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
public class BookRepositoryIntegrationTests {

    private BookRepository underTest;
    private AuthorRepository authorRepository;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest, AuthorRepository authorRepository) {
        this.underTest = underTest;
        this.authorRepository = authorRepository;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        //Create author
        Author author = TestDataUtil.createTestAuthorA();
        //Save author in order fo the id to be create then we save a reference
        //If we dont save the author for the id to be created then we break the flow of the test
        Author saved =  authorRepository.save(author);
        //We use that reference to create the book
        Book book = TestDataUtil.createTestBookA(saved);
        //Here we save the book
        underTest.save(book);
        //Then we try to find it by using the id
        Optional<Book> result = underTest.findById(book.getIsbn());
        //Then we test it
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
        assertThat(result.get().getIsbn()).isEqualTo(book.getIsbn());
        assertThat(result.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(result.get().getAuthor()).isEqualTo(book.getAuthor());

    }


    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        //Created the authors so we have the FKs
        Author author = TestDataUtil.createTestAuthorA();

        //Inserted Author in DB
        authorRepository.save(author);

        //Created the books and passed the author so the method can add the FK
        Book bookA = TestDataUtil.createTestBookA(author);
        Book bookB = TestDataUtil.createTestBookB(author);
        Book bookC = TestDataUtil.createTestBookC(author);

        //Added the Fks
//        bookA.setAuthorId(author.getId());
//        bookB.setAuthorId(author.getId());
//        bookC.setAuthorId(author.getId());

        //Inserted Books in DB
        underTest.save(bookA);
        underTest.save(bookB);
        underTest.save(bookC);

        //Started Find method
        Iterable<Book> result = underTest.findAll();

        //Testing
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testThatBooksCanBeUpdated(){
        //Since book is dependent on author_id as FK we need to create it first
        Author author = TestDataUtil.createTestAuthorA();
        //Save author in the db
        authorRepository.save(author);

        //Created book
        Book bookA = TestDataUtil.createTestBookA(author);

            //JPA sets the FK for us, so we dont need this line anymore
            //bookA.setAuthorId(author.getId());
        underTest.save(bookA);

        //Updated the title in the system
        bookA.setTitle("UPDATED");

        //Commited the update in the db
        underTest.save(bookA);

        //Searched for the updated book
        Optional<Book> result = underTest.findById(bookA.getIsbn());
        //Then we test it
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
        assertThat(result.get().getIsbn()).isEqualTo(bookA.getIsbn());
        assertThat(result.get().getTitle()).isEqualTo("UPDATED");
        assertThat(result.get().getAuthor()).isEqualTo(author);

    }

    @Test
    public void testThatBooksCanBeDeleted(){
        //Since book is dependent on author_id as FK we need to create it first
        Author author = TestDataUtil.createTestAuthorA();
        //Save it to the db
        authorRepository.save(author);

        //Created book
        Book bookA = TestDataUtil.createTestBookA(author);

            //JPA sets the FK for us, so we dont need this line anymore
            //bookA.setAuthorId(author.getId());
        underTest.save(bookA);


        //Commited the update in the db
        underTest.deleteById(bookA.getIsbn());

        //Searched for the updated book
        Optional<Book> result = underTest.findById(bookA.getIsbn());
        assertThat(result).isEmpty();

    }
}

