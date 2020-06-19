package dev.flo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Merge;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import io.smallrye.reactive.messaging.kafka.OutgoingKafkaRecord;

@ApplicationScoped
@Startup
public class WordMPConsumer{

    private static final Logger LOGGER = LoggerFactory.getLogger(WordMPConsumer.class.getName());
    
    
    @Inject
    ObjectMapper objectMapper;

    KafkaConsumer<String, String> consumer;

    @Incoming("words")
    @Outgoing("mapper")
    @Merge
    @Acknowledgment(Strategy.MANUAL)
    public CompletionStage<KafkaRecord<String, String>> process(KafkaRecord<String, String> message){
        LOGGER.info("Incomming words in process {} {}", message.getPayload(), message.getKey());
        return CompletableFuture.completedFuture(message);
    }

    @Incoming("words2")
    public CompletionStage<KafkaRecord<String, String>> process2(KafkaRecord<String, String> message){
        LOGGER.info("Incomming words in process2 {} {}", message.getPayload(), message.getKey());
        return CompletableFuture.completedFuture(message);
    }

    @Incoming("words3")
    public CompletionStage<KafkaRecord<String, String>> process3(KafkaRecord<String, String> message){
        LOGGER.info("Incomming words in process3 {} {}", message.getPayload(), message.getKey());
        return CompletableFuture.completedFuture(message);
    }


    @Incoming("words-internal")
    @Outgoing("kafka-out")
    @Merge
    public CompletionStage<OutgoingKafkaRecord<String, String>> process(Message<String> message){
        return Uni.createFrom().item(()->KafkaRecord.of("words", "key", message.getPayload())).subscribeAsCompletionStage();
    }
}