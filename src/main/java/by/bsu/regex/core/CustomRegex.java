package by.bsu.regex.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public boolean match(String input) {

        List<Character> characterList = new ArrayList<>();

        for (char c : expression.toCharArray()) {
            characterList.add(c);
        }

        System.out.println(characterList);
        System.out.println(normalizeRegexByStars(characterList));

        return false;
    }

    private List<Character> normalizeRegexByStars(List<Character> regex) {
        List<Character> normalized = new ArrayList<>();

        for (int i = 0; i < regex.size(); i++) {
            normalized.add(regex.get(i));

            if (regex.get(i) == STAR) {
                normalized.add(CLOSE);

                for (int j = normalized.size() - 3, counter = 0; j >= 0; j--) {
                    if (normalized.get(j) == CLOSE) {
                        counter++;
                    } else if (normalized.get(j) == OPEN) {
                        counter--;
                    }

                    if (counter == 0) {
                        normalized.add(j, OPEN);
                        break;
                    }
                }
            }
        }

        return normalized;
    }
}
