package com.ighor.postgreSQL.dao.impl;

import com.ighor.postgreSQL.TestDataUtil;
import com.ighor.postgreSQL.dao.AuthorDAO;
import com.ighor.postgreSQL.domain.Author;
import com.ighor.postgreSQL.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImpIntegrationTests {


    private AuthorDAO authorDAO;
    private BookDaoImpl underTest;

    @Autowired
    public BookDaoImpIntegrationTests(BookDaoImpl underTest, AuthorDAO authorDAO){
        this.underTest = underTest;
        this.authorDAO = authorDAO;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthor();
        authorDAO.create(author);
        Book book = TestDataUtil.createTestBook();
        book.setAuthorId(author.getId());
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        //Created the authors so we have the FKs
        Author author = TestDataUtil.createTestAuthorA();

        //Inserted Author in DB
        authorDAO.create(author);

        //Created the books
        Book bookA = TestDataUtil.createTestBookA();
        Book bookB = TestDataUtil.createTestBookB();
        Book bookC = TestDataUtil.createTestBookC();

        //Added the Fks
        bookA.setAuthorId(author.getId());
        bookB.setAuthorId(author.getId());
        bookC.setAuthorId(author.getId());

        //Inserted Books in DB
        underTest.create(bookA);
        underTest.create(bookB);
        underTest.create(bookC);

        //Started Find method
        List<Book> result = underTest.find();

        //Testing
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testThatBooksCanBeUpdated(){
        //Since book is dependent on author_id as FK we need to create it first
        Author author = TestDataUtil.createTestAuthorA();
        authorDAO.create(author);

        //Created book
        Book bookA = TestDataUtil.createTestBookA();

        //Set FKey
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);

        //Updated the title in the system
        bookA.setTitle("UPDATED");

        //Commited the update in the db
        underTest.update(bookA.getIsbn(), bookA);

        //Searched for the updated book
        Optional<Book> result = underTest.findOne(bookA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);

    }

    @Test
    public void testThatBooksCanBeDeleted(){
        //Since book is dependent on author_id as FK we need to create it first
        Author author = TestDataUtil.createTestAuthorA();
        authorDAO.create(author);

        //Created book
        Book bookA = TestDataUtil.createTestBookA();

        //Set FKey
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);


        //Commited the update in the db
        underTest.delete(bookA.getIsbn());

        //Searched for the updated book
        Optional<Book> result = underTest.findOne(bookA.getIsbn());
        assertThat(result).isEmpty();

    }
}
