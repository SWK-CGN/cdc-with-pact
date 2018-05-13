package org.softwerkskammer.cdc.swapi;

import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@RunWith(SpringRestPactRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@PactFolder("pacts")
public abstract class AbstractSpringBootPactTests {
}
