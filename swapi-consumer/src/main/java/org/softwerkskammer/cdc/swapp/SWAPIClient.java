package org.softwerkskammer.cdc.swapp;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.istack.internal.NotNull;
import org.softwerkskammer.cdc.swapp.model.SWFilm;
import org.softwerkskammer.cdc.swapp.model.SWPerson;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
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
        HttpResponse<JsonNode> getPeopleRequest = getRequestFor("/people");
        throw new NotImplementedException();
    }

    public Optional<SWPerson> getPerson(@NotNull final String personID) {
        HttpResponse<JsonNode> getPeopleRequest = getRequestFor("/people/" + personID);
        throw new NotImplementedException();
    }

    public Collection<SWFilm> getFilms() {
        HttpResponse<JsonNode> getFilmsRequest = getRequestFor("/films");
        throw new NotImplementedException();
    }

    public Optional<SWFilm> getFilm(@NotNull final String filmID) {
        HttpResponse<JsonNode> getPeopleRequest = getRequestFor("/films/" + filmID);
        throw new NotImplementedException();
    }


    private HttpResponse<JsonNode> getRequestFor(@NotNull final String endpointPathSuffix) {
        try {
            return Unirest.get(swapiEndpointBaseURI + endpointPathSuffix).asJson();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

}
