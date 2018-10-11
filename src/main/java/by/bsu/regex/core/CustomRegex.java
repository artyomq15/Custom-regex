package by.bsu.regex.core;

public class CustomRegex {

    private String expression;

    private static final char CONCAT = '.';
    private static final char UNION = '|';
    private static final char STAR = '*';
    private static final char OPEN = '(';
    private static final char CLOSE = ')';

    public CustomRegex(String expression) {
        this.expression = expression;
    }

}
