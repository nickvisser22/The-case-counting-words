package library.rest;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import library.analyzers.WordFrequencyAnalyzer;
import library.rest.dto.WordFrequencyDto;
import library.rest.responses.FrequencyForWordResponse;
import library.rest.responses.HighestFrequencyResponse;
import library.rest.responses.MostFrequentNWordsResponse;

import java.util.List;

@Path("/text/frequency")
@Produces(MediaType.APPLICATION_JSON)
public class WordFrequencyResource {

    private final WordFrequencyAnalyzer analyzer;

    @Inject
    public WordFrequencyResource(WordFrequencyAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    @GET
    @Path("/highest")
    public Response getHighestFrequency(@QueryParam("text") String text) {
        final int highestFrequency = this.analyzer.calculateHighestFrequency(text);

        return Response
                .ok(new HighestFrequencyResponse(highestFrequency))
                .build();
    }

    @GET
    @Path("/word")
    public Response getFrequencyForWord(
            @NotBlank(message = "'text' query parameter is required")
            @QueryParam("text") String text,

            @NotBlank(message = "'word' query parameter is required")
            @QueryParam("word") String word
    ) {
        final int frequencyForWord = analyzer.calculateFrequencyForWord(text, word);

        return Response
                .ok(new FrequencyForWordResponse(frequencyForWord))
                .build();
    }

    @GET
    @Path("/ranked")
    public Response getMostFrequentNWords(
            @NotBlank(message = "'text' query parameter is required")
            @QueryParam("text") String text,

            @Min(value = 1, message = "'n' must be at least 1")
            @QueryParam("n") int n
    ) {
        final List<WordFrequencyDto> mostFrequentNWords = analyzer.calculateMostFrequentNWords(text, n)
                .stream()
                .map(wf -> new WordFrequencyDto(wf.getWord(), wf.getFrequency()))
                .toList();;

        return Response
                .ok(new MostFrequentNWordsResponse(mostFrequentNWords))
                .build();
    }
}
