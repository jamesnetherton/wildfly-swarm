package org.wildfly.swarm.camel.core.runtime;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class CamelHealthDeploymentProducer {

    @Produces
    public Archive camelHealthCheckWar() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "camel-health-checks.war");
        deployment.addClass(CamelHealthCheckResource.class);
        deployment.addAllDependencies();
        return deployment;
    }
}
