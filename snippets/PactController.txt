package org.softwerkskammer.cdc.swapi.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.softwerkskammer.cdc.swapi.SwapiImporter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(PactController.PATH)
public class PactController {

    public static final String PATH = "/pact-state-change";

    private final SwapiImporter swapiImporter;

    public PactController(final SwapiImporter swapiImporter) {
        this.swapiImporter = swapiImporter;
    }

    @PostMapping
    public ResponseEntity<Void> changeState(@RequestBody final JsonNode payload) throws IOException {
        swapiImporter.deleteData();

        String state = payload.get("state").asText();
        if ("provider has data".equalsIgnoreCase(state)) {
            swapiImporter.importData();
        }

        return ResponseEntity.ok().build();
    }

}
