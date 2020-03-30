Write a RESTful service which calculates and returns all the prime numbers up to and including a number provided.

Example
The REST call would look something like http://your.host.com/primes/10 and should return JSON content:

    {
      "Initial":  10,    
      "Primes": [2,3,5,7]
    }

### Requirements ###
- The project must be written in Java 7 / 8.
- The project must use Maven OR Gradle to build, test and run.
- The project must have unit and integration tests.
- The project must be runnable in that the service should be hosted in a container e.g. Tomcat, Jetty, Spring Boot etc.
- You may use any frameworks or libraries for support e.g. Spring MVC, Apache CXF etc.

The project must be accessible from Github.

### Optional Extensions ###
- Deploy the solution to a chosen platform that we can access.
- Consider supporting varying return content types such as XML based, that should be configurable using the requested media type.
- Consider ways to improve overall performance e.g. caching results, concurrent algorithm
- Consider supporting multiple algorithms that can be switched based on optional parameters

### Considerations ###
- Springboot, Micronaut, JAX-RS
- Add JWT for security if required
- @EnableAsync methods for aggregation or long running processes
- Api version - used this over content-negotiation as simpler
- Data envelope data and errors
- HAETOS in response object no required

### Implementation ###
- OpenAPI 3 Spec - http://localhost:8080/v3/api-docs/
- Swagger UI - http://localhost:8080/swagger-ui.html