package dev.flo;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Merge;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;

@ApplicationScoped
public class SeedConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeedConsumer.class.getName());

    @Incoming("seed")
    public CompletionStage<Void> seedint(KafkaRecord<String, String> record){
        if (Integer.valueOf(record.getPayload()) % 4 == 0)
            LOGGER.info("Consumming "+record);
        return Uni.createFrom().voidItem().subscribeAsCompletionStage();
    }
}