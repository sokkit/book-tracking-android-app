package com.example.mob_dev_portfolio;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BookInfoFragmentTest {

    @Test
    void dateBefore() {
        BookInfoFragment b = new BookInfoFragment();
        assertTrue(b.dateBefore("1/1/2001", "1/1/2010"));
    }
}