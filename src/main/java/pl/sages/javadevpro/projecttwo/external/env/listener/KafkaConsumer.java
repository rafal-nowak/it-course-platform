package pl.sages.javadevpro.projecttwo.external.env.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.sages.javadevpro.projecttwo.external.env.model.UserTaskEnv;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "Kafka_Task_Report_json", groupId = "group_json",
            containerFactory = "taskKafkaListenerFactory")
    public void consumeJson(UserTaskEnv task) {

        System.out.println("Consumed JSON Task: " + task);

    }
}
