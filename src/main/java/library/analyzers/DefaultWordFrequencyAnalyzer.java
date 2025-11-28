package library.analyzers;

import library.frequencies.DefaultWordFrequency;
import library.frequencies.WordFrequency;
import utils.TextUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultWordFrequencyAnalyzer implements WordFrequencyAnalyzer {
    @Override
    public int calculateHighestFrequency(String text) {
        if (TextUtils.isNullOrBlank(text)) {
            return 0;
        }

        return this.calculateMostFrequentNWords(text, 1) // only one and first needed
                .getFirst()
                .getFrequency();
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        // edge cases
        if (TextUtils.isNullOrBlank(text) || TextUtils.isNullOrBlank(word)) {
            return 0;
        }

        String target = TextUtils.normalize(word); // to lowercase because it must be case-insensitive

        // filter extracted words based on target word and count
        return (int) extractWords(text)
                .stream()
                .filter((String w) -> w.equals(target))
                .count();
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        // in case n = 0 or lower
        if (n <= 0) {
            return List.of();
        }

        List<String> extractedWords = this.extractWords(text);

        // in case extracted words is somehow empty
        if (extractedWords.isEmpty()) {
            return List.of();
        }

        return mapFrequenciesToWordFrequencyList(n, this.mapFrequencies(extractedWords));
    }

    private List<String> extractWords(String text) {
        List<String> words = new ArrayList<>();

        // if text is null or empty return empty arraylist
        if (TextUtils.isNullOrBlank(text)) {
            return words;
        }

        // Matches sequences of letters a-z or A-Z
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(text);

        // Find the next subsequence of the input sequence that matches the pattern
        while (matcher.find()) {
            // Add the word from the input subsequence matched by the previous match
            words.add(matcher.group().toLowerCase());
        }

        return words;
    }

    private Map<String, Integer> mapFrequencies(List<String> words) {
        Map<String, Integer> frequencies = new HashMap<>();

        for (String word : words) {
            frequencies.put(word, frequencies.getOrDefault(word, 0) + 1); // get frequency by word key (or default 0) + 1
        }

        return frequencies;
    }

    private static List<WordFrequency> mapFrequenciesToWordFrequencyList(int n, Map<String, Integer> frequencies) {
        return frequencies
                .entrySet() // make key value pair
                .stream() // stream for sorting and transforming
                .<WordFrequency>map((Map.Entry<String, Integer> e) -> new DefaultWordFrequency(e.getKey(), e.getValue())) // map values to WordFrequency
                .sorted(
                        // same as (a, b) -> {logic...}
                        // sort based on frequency, then alphabetically
                        Comparator.comparing(WordFrequency::getFrequency, Comparator.reverseOrder())
                                .thenComparing(WordFrequency::getWord)
                )
                .limit(n) // limit number n from map
                .toList();
    }
}
