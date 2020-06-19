package dev.flo.nat;

import java.time.Duration;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.flo.Word;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class WordProducer{

    private static final Logger LOGGER = LoggerFactory.getLogger(WordProducer.class.getName());

    @Inject
    ObjectMapper objectMapper;
    
    Producer<String, String> producer ;

    public WordProducer(){

    }

    @PostConstruct
    public void init(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
       
        producer = new KafkaProducer<>(props);
    }

    public void say(String word){
        Word w = new Word();
        w.setContent(word);
        try {
            //LOGGER.info(objectMapper.writeValueAsString(w));
            producer.send(new ProducerRecord<String, String>("words", "categ", objectMapper.writeValueAsString(w)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

	/*@Scheduled(every = "5s")
	public void produceEvery10s() {
        LOGGER.info("Producing word");
        for (int i = 0; i < 100; i++){
            Word w = new Word();
            w.setContent("a word");
            try {
                producer.send(new ProducerRecord<String, String>("words", "categ", objectMapper.writeValueAsString(w)));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }*/
    
    @PreDestroy
    public void shutdown() {
        LOGGER.info("Word producer is stopping");
        producer.close(Duration.ofSeconds(5));
    }

}