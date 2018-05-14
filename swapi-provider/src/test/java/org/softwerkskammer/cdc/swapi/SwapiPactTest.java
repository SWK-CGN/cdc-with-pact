package org.softwerkskammer.cdc.swapi;


import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@RunWith(SpringRestPactRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PactFolder("pacts")
@Provider("SWAPI")
public class SwapiPactTest {

    @Autowired
    private SwapiImporter swapiImporter;

    @TestTarget
    @SuppressWarnings("unused")
    public final static Target target = new SpringBootHttpTarget();

    @State("provider has data")
    public void initializeWithData() throws IOException {
        swapiImporter.deleteData();
        swapiImporter.importData();
    }

    @State("provider has no data")
    public void initializeWithoutData() {
        swapiImporter.deleteData();
    }

}
