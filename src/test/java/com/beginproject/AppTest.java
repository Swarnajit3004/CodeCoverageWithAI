package com.beginproject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    String input1 = "hello";
    App app = new App();
    boolean expected = true;

    @Test
    public void testIfsPalindrome() {
        boolean result = app.isPalindrome(input1);
        assertEquals(expected, result);
    }

}