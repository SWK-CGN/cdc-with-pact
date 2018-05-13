package org.softwerkskammer.cdc.swapi.controllers;

import org.softwerkskammer.cdc.swapi.model.Person;
import org.softwerkskammer.cdc.swapi.repositories.PersonRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class SWPersonController {

    private final PersonRepository personRepository;

    public SWPersonController(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> people() {
        return personRepository.findAll(Sort.by("characterId"));
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<Person> person(final @PathVariable("characterId") long characterId) {
        return personRepository.findByCharacterId(characterId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
