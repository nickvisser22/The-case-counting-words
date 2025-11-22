package library.frequencies;

import utils.TextUtils;

public class DefaultWordFrequency implements WordFrequency {
    private final String word;
    private int frequency;

    public DefaultWordFrequency(String word, Integer frequency) {
        this.word = TextUtils.normalize(word);

        this.frequency = (frequency == null || frequency == 0)
            ? 1
            : frequency;
    }

    @Override
    public String getWord() {
        return this.word;
    }

    @Override
    public int getFrequency() {
        return this.frequency;
    }

    public void incrementFrequency() {
        this.frequency++;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
