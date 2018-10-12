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
        System.out.println(characterList = normalizeRegexByStars(characterList));
        System.out.println(characterList = normalizeRegexByConcat(characterList));

        createTreeFromNormalizedRegex(characterList);


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

    private List<Character> normalizeRegexByConcat(List<Character> regex) {
        List<Character> normalized = new ArrayList<>();

        boolean isCharWanted = false;
        for (int i = 0; i < regex.size(); i++) {

            if (isAllowed(regex.get(i)) && !isCharWanted) {
                normalized.add(OPEN);
                isCharWanted = true;
            }

            if (isCharWanted && !isAllowed(regex.get(i))) {
                normalized.add(CLOSE);
                isCharWanted = false;
            }

            normalized.add(regex.get(i));

            if (isAllowed(regex.get(i)) && i + 1 < regex.size() && isAllowed(regex.get(i + 1))) {
                // normalized.add(CONCAT);
                normalized.add(CLOSE);
                normalized.add(OPEN);
            }
        }

        if (isCharWanted) {
            normalized.add(CLOSE);
        }

        for (int i = 0; i < normalized.size() - 1; i++) {
            if (normalized.get(i) == CLOSE && normalized.get(i + 1) == OPEN) {
                normalized.add(i + 1, CONCAT);
            }
        }

        return normalized;
    }

    private Node createTreeFromNormalizedRegex(List<Character> regex) {

        Node root = null;

        if (regex.size() == 1) {
            return new Node(regex.get(0));
        }

        for (int i = 0; i < regex.size(); i++) {
            if (regex.get(i) == OPEN) {

                int start = i;
                i = getNestedRegexEndIndex(regex, start);
                List<Character> nested = getNestedRegex(regex, start + 1, i - 1);


                root = createTreeFromNormalizedRegex(nested);

                System.out.println(nested);
                System.out.println(root.getValue());
            } else if (regex.get(i) == CONCAT) {
                Node concat = new Node(CONCAT);
                concat.getNodeList().add(root);
                root = concat;
                System.out.println(root.getNodeList().get(0).getValue());
            }
        }

        return root;
    }

    private int getNestedRegexEndIndex(List<Character> regex, int start) {
        int counter = 0;

        for (int i = start; i < regex.size(); i++) {
            if (regex.get(i) == OPEN) {
                counter++;
            } else if (regex.get(i) == CLOSE) {
                counter--;
            }

            if (counter == 0) {
                return i;
            }
        }
        return 0;
    }

    private List<Character> getNestedRegex(List<Character> regex, int start, int end) {
        List<Character> nested = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            nested.add(regex.get(i));
        }

        return nested;
    }

    private boolean isAllowed(Character character) {
        return character != OPEN &&
                character != CLOSE &&
                character != STAR &&
                character != UNION &&
                character != CONCAT;
    }
}
