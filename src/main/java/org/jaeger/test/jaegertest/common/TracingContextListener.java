package org.jaeger.test.jaegertest.common;

import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import io.opentracing.noop.NoopTracerFactory;
import io.opentracing.util.GlobalTracer;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author lkorenko
 */
@WebListener
public class TracingContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(TracingContextListener.class);

    @Inject
    private Tracer tracer;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        GlobalTracer.register(tracer);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    @Produces
    @Singleton
    public static Tracer getTracer() {
        logger.info("TracerProducer initialized");
        Configuration.SamplerConfiguration samplerConfig = new Configuration.SamplerConfiguration();
        samplerConfig.withParam(1);
        samplerConfig.withType("const");
        Configuration.SenderConfiguration senderConfiguration = new Configuration.SenderConfiguration();
        senderConfiguration.withAgentHost("jaeger");
        senderConfiguration.withAgentPort(5775);
        Configuration.ReporterConfiguration reporterConfig = new Configuration.ReporterConfiguration();
        reporterConfig.withLogSpans(true);
        reporterConfig.withSender(senderConfiguration);
        Configuration config = new Configuration("foo");
        config.withReporter(reporterConfig);
        config.withSampler(samplerConfig);
        Tracer tracer = config.getTracer();
        if (tracer == null) {
            logger.info("Could not get a valid OpenTracing Tracer from the classpath. Skipping.");
            tracer = NoopTracerFactory.create();
        }

        logger.info(String.format("Registering %s as the OpenTracing Tracer", tracer.getClass().getName()));
        GlobalTracer.register(tracer);
        return tracer;
    }
}
