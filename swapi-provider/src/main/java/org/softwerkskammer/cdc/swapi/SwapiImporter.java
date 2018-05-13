package org.softwerkskammer.cdc.swapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.softwerkskammer.cdc.swapi.model.Film;
import org.softwerkskammer.cdc.swapi.model.Person;
import org.softwerkskammer.cdc.swapi.repositories.FilmRepository;
import org.softwerkskammer.cdc.swapi.repositories.PersonRepository;
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
    private final FilmRepository filmRepository;
    private final PersonRepository personRepository;

    public SwapiImporter(final ResourceLoader resourceLoader,
                         final ObjectMapper objectMapper,
                         final FilmRepository filmRepository,
                         final PersonRepository personRepository) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
        this.filmRepository = filmRepository;
        this.personRepository = personRepository;
    }

    @PostConstruct
    @Transactional
    public void importData() throws IOException {
        JsonNode swapiFilms = readJson("classpath:/json/films.json");
        JsonNode swapiPeople = readJson("classpath:/json/people.json");

        List<Film> films = toFilms(swapiFilms);
        final Map<String, Person> people = peopleByUrl(swapiPeople);
        final Map<Long, List<String>> swapiPersonUrls = swapiPersonUrlsByEpisodeId(swapiFilms);
        films.forEach(film -> {
            List<String> characterUrls = swapiPersonUrls.get(film.getEpisodeId());
            film.setCharacters(characters(characterUrls, people));
        });

        filmRepository.saveAll(films);
        filmRepository.flush();
    }

    @Transactional
    public void deleteData() {
        filmRepository.deleteAll();
        personRepository.deleteAll();
    }

    private Person toPerson(final JsonNode swapiPerson) {
        String name = swapiPerson.get("name").asText();
        String gender = swapiPerson.get("gender").asText();
        long characterId = idFromUrl(swapiPerson.get("url").asText());
        return new Person(characterId, name, gender);
    }

    private long idFromUrl(final String url) {
        String[] urlParts = url.split("/");
        return Long.parseLong(urlParts[urlParts.length - 1]);
    }

    private Film toFilm(final JsonNode swapiFilm) {
        long episodeId = swapiFilm.get("episode_id").asLong();
        String title = swapiFilm.get("title").asText();
        LocalDate releaseDate = LocalDate.parse(swapiFilm.get("release_date").asText());
        return new Film(episodeId, title, releaseDate);
    }

    private List<Film> toFilms(final JsonNode swapiFilms) {
        return asStream(swapiFilms::elements)
                .map(this::toFilm)
                .collect(toList());
    }

    private Map<String, Person> peopleByUrl(final JsonNode swapiPeople) {
        return asStream(swapiPeople::elements)
                .collect(toMap(
                        swapiPerson -> swapiPerson.get("url").asText(),
                        this::toPerson));
    }

    private Map<Long, List<String>> swapiPersonUrlsByEpisodeId(final JsonNode swapiFilms) {
        return asStream(swapiFilms::elements)
                .collect(toMap(
                        swapiFilm -> swapiFilm.get("episode_id").asLong(),
                        swapiFilm -> asStream(swapiFilm.get("characters")::elements)
                                .map(JsonNode::asText)
                                .collect(toList())));
    }

    private List<Person> characters(final List<String> urls, final Map<String, Person> people) {
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
