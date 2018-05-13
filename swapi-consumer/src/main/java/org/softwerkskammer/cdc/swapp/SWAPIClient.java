package org.softwerkskammer.cdc.swapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import org.apache.http.HttpStatus;
import org.softwerkskammer.cdc.swapp.model.SWFilm;
import org.softwerkskammer.cdc.swapp.model.SWPerson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
            HttpResponse<InputStream> getPeopleRequest = getRequestFor("/people").asBinary();
            if (getPeopleRequest.getStatus() != HttpStatus.SC_OK) {
                return Collections.emptyList();
            }
            return OBJECT_MAPPER.readValue(getPeopleRequest.getBody(), new TypeReference<List<SWPerson>>() {
            });
        } catch (Exception any) {
            throw new RuntimeException(any);
        }
    }

    public Optional<SWPerson> getPerson(final long personID) {
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
            HttpResponse<InputStream> getFilmsRequest = getRequestFor("/films").asBinary();
            if (getFilmsRequest.getStatus() != HttpStatus.SC_OK) {
                return Collections.emptyList();
            }
            return OBJECT_MAPPER.readValue(getFilmsRequest.getBody(), new TypeReference<List<SWFilm>>() {
            });
        } catch (Exception any) {
            throw new RuntimeException(any);
        }
    }

    public Optional<SWFilm> getFilm(final long filmID) {
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

    public final static com.fasterxml.jackson.databind.ObjectMapper OBJECT_MAPPER =
            new com.fasterxml.jackson.databind.ObjectMapper();

    static {
        Unirest.setObjectMapper(
                new ObjectMapper() {

                    @Override
                    public <T> T readValue(String value, Class<T> valueType) {
                        try {
                            return OBJECT_MAPPER.readValue(value, valueType);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public String writeValue(Object value) {
                        try {
                            return OBJECT_MAPPER.writeValueAsString(value);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
    }
}
