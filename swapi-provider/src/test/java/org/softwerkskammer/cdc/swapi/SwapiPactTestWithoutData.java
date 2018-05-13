package org.softwerkskammer.cdc.swapi;


import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import org.springframework.test.annotation.DirtiesContext;

@Provider("SWAPI-Without-Data")
public class SwapiPactTestWithoutData extends AbstractSpringBootPactTests {

    @TestTarget
    @SuppressWarnings("unused")
    public final static Target target = new SpringBootHttpTarget();

    @State("provider is initialized")
    public void initialize() {
    }

}
