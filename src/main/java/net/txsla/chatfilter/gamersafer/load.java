package net.txsla.chatfilter.gamersafer;

import net.txsla.chatfilter.config;
import net.txsla.chatfilter.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class load {
    public static List<Pattern> patterns(int strictness) {
        // profile start
        double start = System.nanoTime();

        // get words
        List<String> words = net.txsla.chatfilter.gamersafer.list.getWords();

        // convert words to patterns
        List<Pattern> patterns = new ArrayList<>();
        for (String word : words) {
            patterns.add(
                    regex.patternFromWord(word, strictness)
            );
        }

        // Profile stop
        if (config.profile) System.out.println("GamerSafer list loaded in " + ((System.nanoTime() - start)/1000000) + " milliseconds" );

        return patterns;
    }
}
