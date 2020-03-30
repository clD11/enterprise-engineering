[![Build Status](https://travis-ci.org/clD11/enterprise-engineering.svg?branch=master)](https://travis-ci.org/clD11/enterprise-engineering/)

## Prime Generator Service ##

_REST service that calculates and returns all prime numbers up to and including a number provided._

#### Run ####
- Clone repo
- Run `EnterpriseEngineeringApplication`

#### Example ####

    http://localhost:8080/primes/10
    
    {
      "initial":  10,    
      "primes": [2,3,5,7]
    }

| Http Method  | Endpoint           | Request                             | Response           |
|:-------------|:-------------------|-------------------------------------|-------------------:|
| GET          | api/v1/primes/{initial} | initial, algorithm (optional)  | JSON and XML       |

#### Swagger ####
- OpenAPI 3 Spec - http://localhost:8080/v3/api-docs/
- Swagger UI - http://localhost:8080/swagger-ui.html

#### Algorithms ####

_Add to request as optional parameter, defaults to Eratosthenes_

Example - `http://localhost:8080/primes/10?algorithm=naive`

- Naive
- Sieve of Eratosthenes
 
#### Content Negotiation ####
- Url parameter - mediaType=xml and mediaType=json
- Accept header - { application/xml, application/json }

#### Http Caching ####
- Prime numbers are can be cached at http level
- Cache-Control max-age 365 days

#### Further Improvements ####
- Add JWT for security if required
- @EnableAsync for concurrent/parallel sieve
- Data envelope for data and errors
- HAETOS in response object not required
