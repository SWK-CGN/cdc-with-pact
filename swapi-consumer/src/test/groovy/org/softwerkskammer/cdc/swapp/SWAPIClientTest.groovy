package org.softwerkskammer.cdc.swapp

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import org.softwerkskammer.cdc.swapp.model.SWPerson
import spock.lang.Specification

import static au.com.dius.pact.consumer.PactVerificationResult.Ok
import static groovy.json.JsonOutput.toJson
import static java.util.Collections.emptyList

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
            given("star wars data exist")
            uponReceiving("a request for person with ID 1'")
            withAttributes(path: '/people/1')
            willRespondWith(status: 200, body:
            /*new PactDslJsonBody()
                    .stringType("id", "xyz")
                    .stringType("name", "Han Solo")
                    .stringMatcher("gender", "Male|Female", "Male").close() */
                    "{\"id\": 1, \"name\": \"Luke Skywalker\", \"gender\": \"male\"}"
                    //toJson(new SWPerson(1L, "Luke Skywalker", "male", emptyList()))
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


    def 'fetches a list of star wars characters'() {
        given:
        providerMock {
            given("star wars data exist")
            uponReceiving("a request for characters'")
            withAttributes(path: '/people')
            willRespondWith(status: 200, body:
                    "[" +
                            "{\"id\": 1, \"name\": \"Luke Skywalker\", \"gender\": \"male\"}," +
                            "{\"id\": 2, \"name\": \"C-3PO\", \"gender\": \"n/a\"}" +
                    "]"
            )
        }
        List<SWPerson> people = emptyList()

        when:
        PactVerificationResult pactResult = providerMock.runTest {
            people = swapiClient.getPeople()
        }

        then:
        pactResult == Ok.INSTANCE
        people.size() == 2
    }

}
