package com.ighor.postgreSQL;

import com.ighor.postgreSQL.domain.Author;
import com.ighor.postgreSQL.domain.Book;

public final class TestDataUtil {
    private TestDataUtil(){
    }

    public static Author createTestAuthor() {
        return Author.builder()
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Author createTestAuthorA() {
        return createTestAuthor();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .name("Thomas Cronin")
                .age(44)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .name("Jesse A Casey")
                .age(24)
                .build();
    }

    public static Book createTestBook(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(author)
                .build();
    }

    public static Book createTestBookA(final Author author) {
        return createTestBook(author);
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-1")
                .title("Beyond the Horizon")
                .author(author)
                .build();
    }

    public static Book createTestBookC(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-2")
                .title("The Last Ember")
                .author(author)
                .build();
    }
}
