package org.softwerkskammer.cdc.swapp

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import groovy.json.JsonBuilder
import org.softwerkskammer.cdc.swapp.model.SWPerson
import spock.lang.Specification

import static au.com.dius.pact.consumer.PactVerificationResult.Ok

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

    def 'fetches a star wars person which does exist'() {
        given:
        providerMock {
            JsonBuilder body = new JsonBuilder()
            body id: 1, name: "Luke Skywalker", gender: "male"

            given("provider has data")
            uponReceiving("a request for person with ID 1'")
            withAttributes(path: '/people/1')
            willRespondWith(status: 200, body: body.toString())
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
