package dev.flo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Merge;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;

@ApplicationScoped
@Startup
public class AnotherConsumer{

    private static final Logger LOGGER = LoggerFactory.getLogger(AnotherConsumer.class.getName());
    

    @Incoming("another")
    @Acknowledgment(Strategy.NONE)
    public CompletionStage<KafkaRecord<String, String>> process(KafkaRecord<String, String> message){
        LOGGER.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! {} {} ", message.getPayload(), message.getKey());
        return CompletableFuture.completedFuture(message);
    }

}