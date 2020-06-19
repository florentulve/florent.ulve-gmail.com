package dev.flo;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.reactive.messaging.annotations.Merge;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;

@ApplicationScoped
public class MapProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapProcessor.class.getName());
    
    @Incoming("mapper")
    @Acknowledgment(Strategy.MANUAL)
    public CompletionStage<Void> process(KafkaRecord<String, String> message){
        LOGGER.info("Processing in mapper {}",message.getPayload());
        return message.ack();
    }

}   