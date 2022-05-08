package com.example.mob_dev_portfolio;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

public class TrophiesFragmentTest {

    @Test
    void differenceInDays() throws ParseException {
        TrophiesFragment t = new TrophiesFragment();
        assertEquals(9, t.differenceInDays("01/01/2020", "10/01/2020"));
    }

}
