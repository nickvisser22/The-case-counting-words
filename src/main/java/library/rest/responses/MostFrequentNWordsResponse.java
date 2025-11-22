package library.rest.responses;

import library.rest.dto.WordFrequencyDto;

import java.util.List;

public record MostFrequentNWordsResponse(List<WordFrequencyDto> mostFrequentNWords) {
}
