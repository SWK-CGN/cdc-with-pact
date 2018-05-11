package org.softwerkskammer.cdc.swapi.repositories;

import org.softwerkskammer.cdc.swapi.model.SWPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SWPersonRepository extends JpaRepository<SWPerson, Long> {
}
