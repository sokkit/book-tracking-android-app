package com.example.mob_dev_portfolio;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BookSearchTest {

    @Test
    void parseAuthor() {
        BookSearch b = new BookSearch("test book", "[\"author one\",\"author two\"]",
                "9395937439", "testthumbnailurl", "test description",
                "01/01/2022");
        assertEquals("author one, author two", b.parseAuthor());
    }


}
