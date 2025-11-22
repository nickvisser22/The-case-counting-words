package library.analyzers;

import library.frequencies.WordFrequency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TextUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultWordFrequencyAnalyzerTest {

    private DefaultWordFrequencyAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        this.analyzer = new DefaultWordFrequencyAnalyzer();
    }

    @Test
    void calculateHighestFrequency() {
        String firstWord = "number";
        String secondWord = "that";
        String thirdWord = "is";

        int expectedHighestFrequency = 10;

        // Arrange text
        String text = TextUtils.repeat(firstWord, 1)
                + TextUtils.repeat(secondWord, 4)
                + TextUtils.repeat(thirdWord, expectedHighestFrequency);

        int highestFrequency = this.analyzer.calculateHighestFrequency(text);

        assertEquals(expectedHighestFrequency, highestFrequency);
    }


    @Test
    void calculateFrequencyTextEmptyOrNull() {
        assertEquals(0, analyzer.calculateHighestFrequency(""));
        assertEquals(0, analyzer.calculateHighestFrequency(null));
    }

    @Test
    void calculateFrequencyForWord() {
        String firstWord = "number";
        String secondWord = "that";
        String thirdWord = "is";

        int firstExpectedFrequency = 10;
        int secondExpectedFrequency = 4;

        // Arrange text
        String text = TextUtils.repeat(firstWord, firstExpectedFrequency)
                + TextUtils.repeat(secondWord, secondExpectedFrequency)
                + TextUtils.repeat(thirdWord, 20);

        assertEquals(firstExpectedFrequency, this.analyzer.calculateFrequencyForWord(text, firstWord));
        assertEquals(secondExpectedFrequency, this.analyzer.calculateFrequencyForWord(text, secondWord));
    }

    @Test
    void calculateFrequencyForWordCaseInsensitive() {
        assertEquals(2, analyzer.calculateFrequencyForWord("The sun the", "the"));
        assertEquals(2, analyzer.calculateFrequencyForWord("The sun the", "THE"));
    }

    @Test
    void calculateMostFrequentNWords() {
        // words used for text in order of frequency
        String firstWord = "number";
        String secondWord = "that";
        String thirdWord = "is";

        // Arrange text
        String text = TextUtils.repeat(firstWord, 4)
                + TextUtils.repeat(secondWord, 3)
                + TextUtils.repeat(thirdWord, 2)
                + "randomword"; // for checking if n is correct and not in final most frequent words

        int n = 3;

        List<WordFrequency> mostFrequentNWords = this.analyzer.calculateMostFrequentNWords(text, n);

        assertEquals(n, mostFrequentNWords.size());
        assertEquals(firstWord, mostFrequentNWords.getFirst().getWord());
        assertEquals(secondWord, mostFrequentNWords.get(1).getWord());
        assertEquals(thirdWord, mostFrequentNWords.get(2).getWord());
    }

    @Test
    void calculateMostFrequentNWordsAlphabetically() {
        // words used for text in order of frequency
        String firstWord = "aaa";
        String secondWord = "bbb";
        String thirdWord = "ccc";

        int frequency = 3;

        // Arrange text
        String text = TextUtils.repeat(firstWord, frequency)
                + TextUtils.repeat(secondWord, frequency)
                + TextUtils.repeat(thirdWord, frequency)
                + "randomword"; // for checking if n is correct and not in final most frequent words

        int n = 3;

        List<WordFrequency> mostFrequentNWords = this.analyzer.calculateMostFrequentNWords(text, n);

        assertEquals(n, mostFrequentNWords.size());
        assertEquals(firstWord, mostFrequentNWords.getFirst().getWord());
        assertEquals(secondWord, mostFrequentNWords.get(1).getWord());
        assertEquals(thirdWord, mostFrequentNWords.get(2).getWord());
    }

    @Test
    void calculateMostFrequentNWordsEmptyOrNullText() {
        assertEquals(List.of(), analyzer.calculateMostFrequentNWords("", 2));
        assertEquals(List.of(), analyzer.calculateMostFrequentNWords(null, 4));
    }

    @Test
    void calculateMostFrequentNWordsNZero() {
        assertEquals(List.of(), analyzer.calculateMostFrequentNWords("Text is way way smaller", 0));
    }
}