package net.txsla.chatfilter.gamersafer;

import net.txsla.chatfilter.config;
import net.txsla.chatfilter.filters;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class list {
    public static final String list_url = "https://raw.githubusercontent.com/GamerSafer/word-blocklist/refs/heads/main/full-word-list.csv";
    public static List<String> getWords() {

        Pattern exclude_pattern;
        //build a pattern to match excluded words (from config)
        StringBuilder temp = new StringBuilder();
        for (String ignored : filters.GamerSaferExclude()) temp.append("(^").append(ignored).append(",?$)|");
        if (temp.length() > 0) temp.setLength(temp.length() - 1);
        exclude_pattern = Pattern.compile(temp.toString());

        System.out.println("Exclude Pattern: " + exclude_pattern.toString());

        List<String> words = new ArrayList<>();

        // get words from .csv
        try {
            URL url = new URL(list_url);
            InputStream inputStream = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                // replace all chars after comma for each line
                if (!net.txsla.chatfilter.regex.match(line, exclude_pattern)) {
                    if (line.endsWith(",")) {
                        line = line.substring(0, line.length() - 1);
                    }
                    words.add(line);
                }
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Unable to retrieve GamerSafer word list");
        }

        // debug output
        if (config.debug) {
            System.out.println("\nGamerSafer Word List:");
            for (String word : words) {
                System.out.print(word + " ");
            }
            System.out.println();
        }

        return words;
    }
}
