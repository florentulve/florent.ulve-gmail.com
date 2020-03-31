package dev.flo;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.Startup;

@ApplicationScoped
@Startup
public class WordConsumer implements Runnable{

    private final AtomicBoolean closed = new AtomicBoolean(false);
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WordConsumer.class.getName());
    
    @Inject
    ManagedExecutor managedExecutor;

    @Inject
    ObjectMapper objectMapper;

    KafkaConsumer<String, String> consumer;

    public WordConsumer(){
    }

    @PostConstruct
    public void init(){
        
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);

        managedExecutor.execute(this);

    }
    
	@Override
	public void run() {

        try {
            consumer.subscribe(Arrays.asList("words"));
            LOGGER.info("Start consuming words");
            while (!closed.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                //logger.info("Polling has fetch "+records.count()+ " record ");
                for (ConsumerRecord<String, String> record : records) {
                    try {
                        Word word = objectMapper.readValue(record.value(), Word.class);
                        LOGGER.info(String.format("%s %s",record.key(), word.getContent()));
                    } catch (JsonProcessingException e) {
                        LOGGER.error("Bad JSON", e);
                    }
                }
            }
        } catch (WakeupException e) {
            // Ignore exception if closing
            if (!closed.get()) throw e;
        } finally {
            LOGGER.info("Word consumer is stopping");
            consumer.close(Duration.ofSeconds(5));
        }

    }
    
    @PreDestroy
    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }
}