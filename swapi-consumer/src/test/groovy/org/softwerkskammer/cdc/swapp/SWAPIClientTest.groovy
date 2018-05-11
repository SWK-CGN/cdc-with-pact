package org.softwerkskammer.cdc.swapp

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import spock.lang.Specification

import static au.com.dius.pact.consumer.PactVerificationResult.Ok;

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

    def 'fetches star wars films'() {
        given:
        providerMock {
            given('several star wars films do exist')
            uponReceiving('a request for films')
            withAttributes(path: '/films')
            willRespondWith(status: 200)
        }

        when:
        PactVerificationResult pactResult = providerMock.runTest {
            swapiClient.getFilms()
        }

        then:
        pactResult == Ok.INSTANCE
    }

}
