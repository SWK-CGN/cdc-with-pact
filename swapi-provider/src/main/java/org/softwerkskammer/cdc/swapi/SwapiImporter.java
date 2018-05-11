package org.softwerkskammer.cdc.swapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.softwerkskammer.cdc.swapi.model.SWFilm;
import org.softwerkskammer.cdc.swapi.model.SWPerson;
import org.softwerkskammer.cdc.swapi.repositories.SWFilmRepository;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Component
public class SwapiImporter {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private final SWFilmRepository filmRepository;

    public SwapiImporter(final ResourceLoader resourceLoader,
                         final ObjectMapper objectMapper,
                         final SWFilmRepository filmRepository) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
        this.filmRepository = filmRepository;
    }

    @PostConstruct
    @Transactional
    public void importData() throws IOException {
        JsonNode jsonFilms = readJson("classpath:/json/films.json");
        JsonNode jsonPeople = readJson("classpath:/json/people.json");

        List<SWFilm> films = toSWFilms(jsonFilms);
        final Map<String, SWPerson> people = peopleByUrl(jsonPeople);
        final Map<Long, List<String>> urls = urlsByEpisodeId(jsonFilms);
        films.forEach(film -> {
            List<String> episodeUrls = urls.get(film.getEpisodeId());
            List<SWPerson> episodeCharacters = characters(episodeUrls, people);
            film.setCharacters(episodeCharacters);
        });

        filmRepository.saveAll(films);
        filmRepository.flush();
    }

    private SWPerson toSWPerson(final JsonNode jsonPerson) {
        String name = jsonPerson.get("name").asText();
        String gender = jsonPerson.get("gender").asText();
        long characterId = characterId(jsonPerson.get("url").asText());
        return new SWPerson(characterId, name, gender);
    }

    private long characterId(final String url) {
        String[] urlParts = url.split("/");
        return Long.parseLong(urlParts[urlParts.length - 1]);
    }

    private SWFilm toSWFilm(final JsonNode jsonFilm) {
        long episodeId = jsonFilm.get("episode_id").asLong();
        String title = jsonFilm.get("title").asText();
        LocalDate releaseDate = LocalDate.parse(jsonFilm.get("release_date").asText());
        return new SWFilm(episodeId, title, releaseDate);
    }

    private List<SWFilm> toSWFilms(final JsonNode jsonFilms) {
        return asStream(jsonFilms::elements)
                .map(this::toSWFilm)
                .collect(toList());
    }

    private Map<String, SWPerson> peopleByUrl(final JsonNode jsonPeople) {
        return asStream(jsonPeople::elements)
                .collect(toMap(jsonPerson -> jsonPerson.get("url").asText(), this::toSWPerson));
    }

    private Map<Long, List<String>> urlsByEpisodeId(final JsonNode jsonFilms) {
        return asStream(jsonFilms::elements)
                .collect(toMap(
                        jsonFilm -> jsonFilm.get("episode_id").asLong(),
                        jsonFilm -> asStream(jsonFilm.get("characters")::elements)
                                .map(JsonNode::asText)
                                .collect(toList())));
    }

    private List<SWPerson> characters(final List<String> urls, final Map<String, SWPerson> people) {
        return urls.stream()
                .filter(people::containsKey)
                .map(people::get)
                .collect(toList());
    }

    private JsonNode readJson(final String resource) throws IOException {
        InputStream inputStream = resourceLoader.getResource(resource).getInputStream();
        String jsonString = IOUtils.toString(inputStream, "UTF-8");
        return objectMapper.readTree(jsonString);
    }

    private Stream<JsonNode> asStream(final Iterable<JsonNode> elements) {
        return StreamSupport.stream(elements.spliterator(), false);
    }

}
