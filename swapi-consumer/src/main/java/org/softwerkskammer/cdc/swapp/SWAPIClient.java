package org.softwerkskammer.cdc.swapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import org.apache.http.HttpStatus;
import org.softwerkskammer.cdc.swapp.model.SWFilm;
import org.softwerkskammer.cdc.swapp.model.SWPerson;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

public class SWAPIClient {

    private final String swapiEndpointBaseURI;

    public SWAPIClient(final String swapiEndpointBaseURI) {
        try {
            this.swapiEndpointBaseURI = new URI(swapiEndpointBaseURI).toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Collection<SWPerson> getPeople() {
        try {
            HttpResponse<JsonNode> getPeopleRequest = getRequestFor("/people").asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        throw new NotImplementedException();
    }

    public Optional<SWPerson> getPerson(final String personID) {
        try {
            HttpResponse<SWPerson> getPersonResponse = getRequestFor("/people/" + personID)
                    .asObject(SWPerson.class);
            if (getPersonResponse.getStatus() != HttpStatus.SC_OK) {
                return Optional.empty();
            }
            return Optional.of(getPersonResponse.getBody());
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<SWFilm> getFilms() {
        try {
            HttpResponse<JsonNode> getFilmsRequest = getRequestFor("/films").asJson();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        throw new NotImplementedException();
    }

    public Optional<SWFilm> getFilm(final String filmID) {
        try {
            HttpResponse<SWFilm> getFilmResponse = getRequestFor("/films/" + filmID)
                    .asObject(SWFilm.class);
            if (getFilmResponse.getStatus() != HttpStatus.SC_OK) {
                return Optional.empty();
            }
            return Optional.of(getFilmResponse.getBody());
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest getRequestFor(final String endpointPathSuffix) {
        return Unirest.get(swapiEndpointBaseURI + endpointPathSuffix);
    }

    static {
        Unirest.setObjectMapper(
                new ObjectMapper() {
                    com.fasterxml.jackson.databind.ObjectMapper mapper
                            = new com.fasterxml.jackson.databind.ObjectMapper();

                    @Override
                    public <T> T readValue(String value, Class<T> valueType) {
                        try {
                            return mapper.readValue(value, valueType);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public String writeValue(Object value) {
                        try {
                            return mapper.writeValueAsString(value);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
    }
}
