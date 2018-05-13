package org.softwerkskammer.cdc.swapp

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import org.softwerkskammer.cdc.swapp.model.SWPerson
import spock.lang.Specification

import static au.com.dius.pact.consumer.PactVerificationResult.Ok
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

    def 'fetches a star wars person which does exist'() {
        given:
        providerMock {
            hasPactWith 'SWAPI-With-Data'
            given("provider is initialized")
            uponReceiving("a request for person with ID 1'")
            withAttributes(path: '/people/1')
            willRespondWith(status: 200)
            withBody(mimeType: JSON.toString()){
                id integer(1)
                name regexp(~/.+/, 'Luke Skywalker')
                gender regexp(~/male|female|n\/a|hermaphrodite/, 'male')
            }
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

    def 'fetches a star wars person which does not exist'() {
        given:
        providerMock {
            hasPactWith 'SWAPI-Without-Data'
            given("provider is initialized")
            uponReceiving("a request for person with ID 1'")
            withAttributes(path: '/people/1')
            willRespondWith(status: 404)
        }
        Optional<SWPerson> person = Optional.empty()

        when:
        PactVerificationResult pactResult = providerMock.runTest {
            person = swapiClient.getPerson(1L)
        }

        then:
        pactResult == Ok.INSTANCE
        ! person.isPresent()
    }


    def 'fetches a list of people'() {
        given:
        providerMock {
            hasPactWith 'SWAPI-With-Data'
            given("provider is initialized")
            uponReceiving("a request for people'")
            withAttributes(path: '/people')
            willRespondWith(status: 200)
            withBody(mimeType: JSON.toString()) {
                items eachLike(2 , {
                    id integer(1)
                    name regexp(~/.+/, 'Luke Skywalker')
                    gender regexp(~/male|female|n\/a|hermaphrodite/, 'male')
                })
            }
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
