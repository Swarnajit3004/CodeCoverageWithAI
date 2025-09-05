package com.beginproject;

public class App {

    public boolean isPalindrome(String input) {
        if(input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        if (input.equals(reverse(input))) {
            return true;
        }
        else
            return false;
    }

    private String reverse(String input) {
        String rev = "";
        for (int i = 0; i < input.length(); i++) {
            rev = rev + input.charAt(i);
        }
        return rev;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
