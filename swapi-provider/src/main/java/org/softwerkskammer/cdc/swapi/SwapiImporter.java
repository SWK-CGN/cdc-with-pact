package org.softwerkskammer.cdc.swapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.softwerkskammer.cdc.swapi.model.SWFilm;
import org.softwerkskammer.cdc.swapi.model.SWPerson;
import org.softwerkskammer.cdc.swapi.repositories.SWFilmRepository;
import org.softwerkskammer.cdc.swapi.repositories.SWPersonRepository;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

@Component
public class SwapiImporter {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private final SWFilmRepository filmRepository;
    private final SWPersonRepository personRepository;

    public SwapiImporter(final ResourceLoader resourceLoader,
                         final ObjectMapper objectMapper,
                         final SWFilmRepository filmRepository,
                         final SWPersonRepository personRepository) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
        this.filmRepository = filmRepository;
        this.personRepository = personRepository;
    }

    @PostConstruct
    public void importData() throws IOException {
        JsonNode films = objectMapper.readTree(readResourceAsString("classpath:/json/films.json"));
        films.elements().forEachRemaining(this::importFilm);

        JsonNode people = objectMapper.readTree(readResourceAsString("classpath:/json/people.json"));
        people.elements().forEachRemaining(this::importPerson);
    }

    private void importFilm(final JsonNode film) {
        long episodeId = film.get("episode_id").asLong();
        String title = film.get("title").asText();
        LocalDate releaseDate = LocalDate.parse(film.get("release_date").asText());
        filmRepository.saveAndFlush(new SWFilm(episodeId, title, releaseDate));
    }

    private void importPerson(final JsonNode person) {
        String name = person.get("name").asText();
        String gender = person.get("gender").asText();
        long characterId = characterId(person.get("url"));
        personRepository.saveAndFlush(new SWPerson(characterId, name, gender));
    }

    private long characterId(final JsonNode url) {
        final String[] urlParts = url.asText().split("/");
        return Long.parseLong(urlParts[urlParts.length - 1]);
    }

    private String readResourceAsString(final String resource) throws IOException {
        final InputStream inputStream = resourceLoader.getResource(resource).getInputStream();
        return IOUtils.toString(inputStream, "UTF-8");
    }

}
