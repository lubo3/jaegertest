/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jaeger.test.jaegertest.controllers;

import io.opentracing.contrib.jaxrs2.client.ClientTracingFeature.Builder;
import io.opentracing.util.GlobalTracer;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author lkorenko
 */
@Named("vc")
@SessionScoped
public class ViewController implements Serializable {

    private String status;

    public void callHi() {
        status = dummyClient();
    }

    private String dummyClient() {
        Client client = ClientBuilder.newClient(new ClientConfig().register(new Builder(GlobalTracer.get()).withTraceSerialization(false).build()));
        WebTarget webTarget = client.target("http://localhost:9080/jaegertest/rest/").path("endpoint").path("hi");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        return String.valueOf(response.getStatus());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
