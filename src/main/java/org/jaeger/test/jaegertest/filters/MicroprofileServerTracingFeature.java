package org.jaeger.test.jaegertest.filters;

import io.opentracing.Tracer;
import io.opentracing.contrib.jaxrs2.server.OperationNameProvider.ClassNameOperationName;
import io.opentracing.contrib.jaxrs2.server.ServerTracingDynamicFeature;
import io.opentracing.contrib.jaxrs2.server.ServerTracingDynamicFeature.Builder;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author lkorenko
 */
@Provider
public class MicroprofileServerTracingFeature implements DynamicFeature {

    private ServerTracingDynamicFeature server;

    public MicroprofileServerTracingFeature() {
        Instance<Tracer> tracerInstance = CDI.current().select(Tracer.class);
        this.server = new Builder(tracerInstance.get())
                .withOperationNameProvider(ClassNameOperationName.newBuilder())
                .withTraceSerialization(false)
                .build();
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        server.configure(resourceInfo, context);
    }
}
