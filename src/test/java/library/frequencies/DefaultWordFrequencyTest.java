package library.frequencies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultWordFrequencyTest {

    private DefaultWordFrequency wordFrequency;
    private final String testWord = "testWord";
    private int frequency = 1;

    @BeforeEach
    void setUp() {
        this.wordFrequency = new DefaultWordFrequency(this.testWord, this.frequency);
    }

    @Test
    void getWord() {
        String word = this.wordFrequency.getWord();

        // to lowercase because wordFrequency should convert to lowercase
        assertEquals(this.testWord.toLowerCase(), word);
    }

    @Test
    void getFrequency() {
        assertEquals(this.frequency, this.wordFrequency.getFrequency());
    }

    @Test
    void incrementFrequency() {
        // check if initial frequency is set correctly
        assertEquals(this.frequency, this.wordFrequency.getFrequency());

        this.wordFrequency.incrementFrequency();

        // check if increment is done correctly
        assertEquals(++this.frequency, this.wordFrequency.getFrequency());
    }

    @Test
    void setFrequency() {
        int newFrequency = 10;

        // check initial frequency value
        assertEquals(this.frequency, this.wordFrequency.getFrequency());

        this.wordFrequency.setFrequency(newFrequency);

        // check if new frequency set correctly
        assertEquals(newFrequency, this.wordFrequency.getFrequency());
    }
}