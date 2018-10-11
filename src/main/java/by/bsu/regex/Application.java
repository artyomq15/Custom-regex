package by.bsu.regex;

import by.bsu.regex.core.CustomRegex;

public class Application {
    public static void main(String[] args) {
        CustomRegex customRegex = new CustomRegex("mc*(m|t)*m(c)*");
        customRegex.match("input");

    }
}
