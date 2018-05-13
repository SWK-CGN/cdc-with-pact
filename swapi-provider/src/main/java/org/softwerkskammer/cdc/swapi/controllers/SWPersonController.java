package org.softwerkskammer.cdc.swapi.controllers;

import org.softwerkskammer.cdc.swapi.model.Person;
import org.softwerkskammer.cdc.swapi.repositories.PersonRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(SWPersonController.PATH)
public class SWPersonController {

    static final String PATH = "/people";

    static String path(final UriComponentsBuilder uriComponentsBuilder) {
        return uriComponentsBuilder.toUriString() + PATH;
    }

    private final PersonRepository personRepository;

    public SWPersonController(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<SWPerson> people(final UriComponentsBuilder uriComponentsBuilder) {
        return personRepository.findAll(Sort.by("characterId")).stream()
                .map(person -> new SWPerson(person, SWFilmController.path(uriComponentsBuilder)))
                .collect(toList());
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<SWPerson> person(final @PathVariable("characterId") long characterId,
                                           final UriComponentsBuilder uriComponentsBuilder) {
        return personRepository.findByCharacterId(characterId)
                .map(toSWPerson(uriComponentsBuilder))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Function<Person, SWPerson> toSWPerson(final UriComponentsBuilder uriComponentsBuilder) {
        return person -> new SWPerson(person, SWFilmController.path(uriComponentsBuilder));
    }

}
