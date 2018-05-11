package org.softwerkskammer.cdc.swapi.repositories;

import org.softwerkskammer.cdc.swapi.model.SWPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SWPersonRepository extends JpaRepository<SWPerson, Long> {

    Optional<SWPerson> findByCharacterId(long characterId);

}
