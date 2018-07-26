package org.jaeger.test.jaegertest.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jaeger.test.jaegertest.services.TestService;

@Path("endpoint")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class TestRestService {

    @EJB
    private TestService testService;

    @GET
    @Path("hi")
    public String hi() {
        return testService.hi();
    }
}
