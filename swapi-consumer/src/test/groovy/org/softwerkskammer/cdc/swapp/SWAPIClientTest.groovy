package org.softwerkskammer.cdc.swapp

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import org.softwerkskammer.cdc.swapp.model.SWPerson
import spock.lang.Specification

import static au.com.dius.pact.consumer.PactVerificationResult.Ok
import static groovy.json.JsonOutput.toJson

class SWAPIClientTest extends Specification {

    SWAPIClient swapiClient
    PactBuilder providerMock

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
            given("Luke Skywalker with ID 1 exists")
            uponReceiving("a request for person with ID 1'")
            withAttributes(path: '/people/1')
            willRespondWith(status: 200, body:
            /*new PactDslJsonBody()
                    .stringType("id", "xyz")
                    .stringType("name", "Han Solo")
                    .stringMatcher("gender", "Male|Female", "Male").close() */
                    toJson(new SWPerson(1L, "Luke Skywalker", "male"))
            )
        }
        Optional<SWPerson> person = Optional.empty()

        when:
        PactVerificationResult pactResult = providerMock.runTest {
            person = swapiClient.getPerson(1L)
        }

        then:
        pactResult == Ok.INSTANCE
        person.isPresent()
        person.get().id == 1L
        person.get().name == "Luke Skywalker"
        person.get().gender == "male"
    }

}
