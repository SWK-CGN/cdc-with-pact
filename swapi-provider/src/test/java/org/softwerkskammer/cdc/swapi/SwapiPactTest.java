package org.softwerkskammer.cdc.swapi;


import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.softwerkskammer.cdc.swapi.controllers.PactController;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRestPactRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PactFolder("pacts")
@Provider("SWAPI")
public class SwapiPactTest {

    private static final String STATE_PROVIDER_HAS_DATA = "provider has data";
    private static final String STATE_PROVIDER_HAS_NO_DATA = "provider has no data";

    @TestTarget
    @SuppressWarnings("unused")
    public final static Target target = new SpringBootHttpTarget();

    @LocalServerPort
    int localServicePort;

    private RestTemplate restTemplate;
    private String stateChangeUrl;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        stateChangeUrl = String.format("http://localhost:%d/%s", localServicePort, PactController.PATH);
    }

    @State(STATE_PROVIDER_HAS_DATA)
    public void initializeWithData() {
        changeStateTo(STATE_PROVIDER_HAS_DATA);
    }

    @State(STATE_PROVIDER_HAS_NO_DATA)
    public void initializeWithoutData() {
        changeStateTo(STATE_PROVIDER_HAS_NO_DATA);
    }

    private void changeStateTo(final String state) {
        restTemplate.postForEntity(stateChangeUrl, requestForState(state), Void.class);
    }

    @NotNull
    private HttpEntity<Map<String, String>> requestForState(final String state) {
        Map<String, String> payload = new HashMap<>();
        payload.put("state", state);
        return new HttpEntity<>(payload);
    }

}
