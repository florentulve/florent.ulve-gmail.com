package dev.flo;

import javax.enterprise.event.Observes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class.getName());

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("Bridge app is starting...");

    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("Bridge app is stopping...");
    }

}