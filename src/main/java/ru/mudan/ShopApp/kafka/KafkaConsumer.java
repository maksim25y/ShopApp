package ru.mudan.ShopApp.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.mudan.ShopApp.services.EmailSenderService;

@Service
public class KafkaConsumer {
    private final EmailSenderService senderService;
    @Autowired
    public KafkaConsumer(EmailSenderService senderService) {
        this.senderService = senderService;
    }

    @KafkaListener(topics = "email",groupId = "testId")
    public void listen(String email){
        String[] res = email.split(" ");
        senderService.sendSimpleEmail(res[0], "Подтверждение почты", res[1]);
    }
}
