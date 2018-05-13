package org.softwerkskammer.cdc.swapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.softwerkskammer.cdc.swapp.model.SWFilm;
import org.softwerkskammer.cdc.swapp.model.SWPerson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SWAPIClient {

    private final String swapiEndpointBaseURI;

    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper =
            new com.fasterxml.jackson.databind.ObjectMapper();

    public SWAPIClient(final String swapiEndpointBaseURI) {
        try {
            this.swapiEndpointBaseURI = new URI(swapiEndpointBaseURI).toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Collection<SWPerson> getPeople() {
        try {
            HttpResponse<String> getPeopleRequest = getRequestFor("/people");
            if (getPeopleRequest.getStatus() != HttpStatus.SC_OK) {
                return Collections.emptyList();
            }
            JsonNode jsonNode = new JsonNode(getPeopleRequest.getBody());
            return objectMapper.readValue(jsonNode.getObject().getJSONArray("items").toString(), new TypeReference<List<SWPerson>>() {
            });
        } catch (Exception any) {
            throw new RuntimeException(any);
        }
    }

    public Optional<SWPerson> getPerson(final long personID) {
        try {
            HttpResponse<String> getPersonResponse = getRequestFor("/people/" + personID);
            if (getPersonResponse.getStatus() != HttpStatus.SC_OK) {
                return Optional.empty();
            }
            return Optional.of(objectMapper.readValue(getPersonResponse.getBody(), SWPerson.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<SWFilm> getFilms() {
        try {
            HttpResponse<String> getFilmsRequest = getRequestFor("/films");
            if (getFilmsRequest.getStatus() != HttpStatus.SC_OK) {
                return Collections.emptyList();
            }
            return objectMapper.readValue(getFilmsRequest.getBody(), new TypeReference<List<SWFilm>>() {
            });
        } catch (Exception any) {
            throw new RuntimeException(any);
        }
    }

    public Optional<SWFilm> getFilm(final long filmID) {
        try {
            HttpResponse<String> getFilmResponse = getRequestFor("/films/" + filmID);
            if (getFilmResponse.getStatus() != HttpStatus.SC_OK) {
                return Optional.empty();
            }
            return Optional.of(objectMapper.readValue(getFilmResponse.getBody(), SWFilm.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse<String> getRequestFor(final String endpointPathSuffix) throws UnirestException {
        return Unirest.get(swapiEndpointBaseURI + endpointPathSuffix).asString();
    }


}
