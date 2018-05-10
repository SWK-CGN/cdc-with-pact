# cdc-with-pact

- Schreiben eines Integrationstests für einen gegebenen HTTP Client:
 - Service Module (SWAPI) -> https://swapi.co
    - Endpoints:
      - [GET] People (Collection und Single)
      - [GET] Films (Collection und Single)
      - Persistenz mit H2 und Spring Data JPA
      - How to initialize states!?
 - Consumer Module (SWAPP)
    - HttpClient mit Operationen (mit Unirest?):
      - [GET] People (Collection und Single)
      - [GET] Films (Collection und Single)
      - Tests: Groovy - Spock & Groovy Pact Builder
 