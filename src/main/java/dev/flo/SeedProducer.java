package dev.flo;

import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.streams.operators.PublisherBuilder;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Publisher;

import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import io.smallrye.reactive.messaging.kafka.OutgoingKafkaRecord;

@ApplicationScoped
public class SeedProducer {

    @Outgoing("kafka-out")
    public Publisher<OutgoingKafkaRecord<String, String>> seedint() {
        AtomicInteger counter = new AtomicInteger();
        PublisherBuilder<OutgoingKafkaRecord<String, String>> stream = ReactiveStreams
                .generate(() -> KafkaRecord.of("seed", "number", String.valueOf(counter.getAndIncrement())))
                .limit(50)
                ;
        return stream.buildRs();
    }
}