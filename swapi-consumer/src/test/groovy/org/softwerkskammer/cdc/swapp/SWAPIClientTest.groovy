package org.softwerkskammer.cdc.swapp

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import org.softwerkskammer.cdc.swapp.model.SWPerson
import spock.lang.Specification

import static au.com.dius.pact.consumer.PactVerificationResult.Ok
import static groovy.json.JsonOutput.toJson;
class SWAPIClientTest extends Specification {


    SWAPIClient swapiClient;
    PactBuilder providerMock;


    def setup() {
        swapiClient = new SWAPIClient('http://localhost:1977')

        providerMock = new PactBuilder()
        providerMock {
            serviceConsumer 'SWAPP'
            hasPactWith 'SWAPI'
            port 1977
        }
    }

    def 'fetches a star wars character which does exist'() {
        given:
        providerMock {
            given("a person with id 'xyz' does exist")
            uponReceiving("a request for person with id 'xyz'")
            withAttributes(path: '/people/xyz')
            willRespondWith(status: 200, body:
            /*new PactDslJsonBody()
                    .stringType("id", "xyz")
                    .stringType("name", "Han Solo")
                    .stringMatcher("gender", "Male|Female", "Male").close() */
                    toJson(new SWPerson("xyz", "Han Solo", "Male"))
            )
        }
        Optional<SWPerson> person = Optional.empty()

        when:
        PactVerificationResult pactResult = providerMock.runTest {
            person = swapiClient.getPerson("xyz")
        }

        then:
        pactResult == Ok.INSTANCE
        person.isPresent()
        person.get().id == "xyz"
        person.get().name == "Han Solo"
        person.get().gender == "Male"
    }

}
