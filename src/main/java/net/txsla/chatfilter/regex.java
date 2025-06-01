package net.txsla.chatfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class regex {
    public static Pattern patternFromWord(String word, int strict) {
        // var word should ONLY EVERY BE A WORD, phrases might be mishandled
        StringBuilder pattern = new StringBuilder();
        pattern.append("\\b");

        // strictness level 0
        // exact match, case-insensitive
        if (strict == 0) {
            for (int i = 0; i < word.length(); i++) {
                // Read single char as a String
                String letter = String.valueOf(word.charAt(i));

                // add to regex pattern
                pattern.append("(?i)").append(letter);
            }
        }

        // strictness level 1
        // extra letters, case-insensitive
        if (strict == 1) {
            for (int i = 0; i < word.length(); i++) {
                // Read single char as a String
                String letter = String.valueOf(word.charAt(i));

                pattern.append("(?i)").append(letter).append("+");
            }
        }

        // strictness level 2
        // whitespace, extra letters, case-insensitive
        if (strict == 2) {
            for (int i = 0; i < word.length(); i++) {
                // Read single char as a String
                String letter = String.valueOf(word.charAt(i));

                pattern.append("(?i)").append(letter).append("+(\\W|\\d|_)*");
            }
        }

        return Pattern.compile( pattern.toString() );
    }
    public static List<Pattern> stringListToRegex(List<String> input) {
        List<Pattern> patterns = new ArrayList<>();
        for (String regex_string : input) {
            patterns.add( Pattern.compile(regex_string));
        }
        return patterns;
    }
}
