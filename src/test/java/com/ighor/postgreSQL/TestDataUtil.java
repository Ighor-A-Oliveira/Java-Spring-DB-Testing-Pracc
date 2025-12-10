package com.ighor.postgreSQL;

import com.ighor.postgreSQL.domain.Author;
import com.ighor.postgreSQL.domain.Book;

public final class TestDataUtil {

    private TestDataUtil(){

    }


    public static Author createTestAuthor() {
        return new Author().builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Author createTestAuthorA() {
        return createTestAuthor();
    }

    public static Author createTestAuthorB() {
        return new Author().builder()
                .id(2L)
                .name("Thomas Cronin")
                .age(44)
                .build();
    }

    public static Author createTestAuthorC() {
        return new Author().builder()
                .id(3L)
                .name("Jesse A Cassey")
                .age(24)
                .build();
    }

    public static Book createTestBook() {
        return new Book().builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookA() {
        return createTestBook();
    }

    public static Book createTestBookB() {
        return new Book().builder()
                .isbn("978-1-2345-6789-1")
                .title("Beyond the Horizon")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookC() {
        return new Book().builder()
                .isbn("978-1-2345-6789-2")
                .title("The Last Ember")
                .authorId(1L)
                .build();
    }
}
