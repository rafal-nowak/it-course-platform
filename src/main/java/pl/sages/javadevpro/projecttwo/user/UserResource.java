package pl.sages.javadevpro.projecttwo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class UserResource {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "Kafka_Example";

    @GetMapping("/publish/{message}")
    public String post(@PathVariable("message") final String message) {
        kafkaTemplate.send(TOPIC, message);
        return "Published successfully";
    }

    private void nothing() {
       // kafkaTemplate.mess
    }

    @KafkaListener(topics = TOPIC, groupId = "training")
   // @KafkaListener(topics = TOPIC)
    public void onMessage(String message) {
        //log.info("New kafka message: " + message);
        System.out.println("New kafka message: " + message);
    }

}
