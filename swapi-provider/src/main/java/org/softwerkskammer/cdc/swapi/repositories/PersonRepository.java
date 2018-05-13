package org.softwerkskammer.cdc.swapi.repositories;

import org.softwerkskammer.cdc.swapi.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByCharacterId(long characterId);

}
