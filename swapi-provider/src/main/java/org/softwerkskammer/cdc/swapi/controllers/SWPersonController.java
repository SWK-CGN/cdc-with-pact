package org.softwerkskammer.cdc.swapi.controllers;

import org.softwerkskammer.cdc.swapi.model.Person;
import org.softwerkskammer.cdc.swapi.repositories.PersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.function.Function;

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
    public SWCollection<SWPerson> allPeople(final UriComponentsBuilder uriComponentsBuilder) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<SWPerson> person(final @PathVariable("characterId") long characterId,
                                           final UriComponentsBuilder uriComponentsBuilder) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    private Function<Person, SWPerson> toSWPerson(final UriComponentsBuilder uriComponentsBuilder) {
        return person -> new SWPerson(person,
                SWPersonController.path(uriComponentsBuilder),
                SWFilmController.path(uriComponentsBuilder));
    }

}
