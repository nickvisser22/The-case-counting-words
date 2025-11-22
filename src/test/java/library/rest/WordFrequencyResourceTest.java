package library.rest;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import library.analyzers.DefaultWordFrequencyAnalyzer;
import library.analyzers.WordFrequencyAnalyzer;
import library.rest.responses.FrequencyForWordResponse;
import library.rest.responses.HighestFrequencyResponse;
import library.rest.responses.MostFrequentNWordsResponse;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordFrequencyResourceTest extends JerseyTest {

    private static final String ENDPOINT = "/text/frequency/";

    @Override
    protected Application configure() {
        return new ResourceConfig()
                .property("jersey.config.server.wadl.disableWadl", true)
                .register(WordFrequencyResource.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(DefaultWordFrequencyAnalyzer.class)
                                .to(WordFrequencyAnalyzer.class);
                    }
                });
    }

    @Test
    void getHighestFrequency() {
        Response response = target(ENDPOINT + "highest")
                .queryParam("text", "The sun shines over the lake")
                .request()
                .get();

        assertEquals(200, response.getStatus());

        HighestFrequencyResponse dto = response.readEntity(library.rest.responses.HighestFrequencyResponse.class);

        assertEquals(2, dto.highestFrequency());
    }

    @Test
    void getFrequencyForWord() {
        Response response = target(ENDPOINT + "word")
                .queryParam("text", "The sun shines over the lake")
                .queryParam("word", "shines")
                .request()
                .get();

        assertEquals(200, response.getStatus());

        FrequencyForWordResponse dto = response.readEntity(library.rest.responses.FrequencyForWordResponse.class);

        assertEquals(1, dto.frequencyForWord());
    }

    @Test
    void getMostFrequentNWords() {
        int n = 3;
        Response response = target(ENDPOINT + "ranked")
                .queryParam("text", "The sun shines over the lake")
                .queryParam("n", n)
                .request()
                .get();

        assertEquals(200, response.getStatus());

        MostFrequentNWordsResponse dto = response.readEntity(library.rest.responses.MostFrequentNWordsResponse.class);

        assertEquals("the", dto.mostFrequentNWords().getFirst().word());
        assertEquals(n, dto.mostFrequentNWords().size());
    }
}