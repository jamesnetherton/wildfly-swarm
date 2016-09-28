package org.wildfly.swarm.camel.core.runtime;

import org.apache.camel.CamelContext;
import org.wildfly.extension.camel.CamelAware;
import org.wildfly.extension.camel.CamelContextRegistry;
import org.wildfly.swarm.monitor.Health;
import org.wildfly.swarm.monitor.HealthStatus;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Optional;
import java.util.Set;

@CamelAware
@Path("/")
public class CamelHealthCheckResource {

    @GET
    @Path("/camel")
    @Health
    public HealthStatus checkCamelContexts() throws NamingException {
        CamelContextRegistry contextRegistry = InitialContext.doLookup("java:jboss/camel/CamelContextRegistry");
        Set<CamelContext> camelContexts = contextRegistry.getCamelContexts();
        Optional<CamelContext> optional = camelContexts.stream()
            .filter(context -> !context.getStatus().isStarted())
            .findAny();

        return optional.isPresent() || camelContexts.isEmpty() ? HealthStatus.down() : HealthStatus.up();
    }
}