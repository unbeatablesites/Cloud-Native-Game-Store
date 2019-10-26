package com.company.retailqueueconsumer.util;

import com.company.retailqueueconsumer.RetailQueueConsumerApplication;
import com.company.retailqueueconsumer.util.feign.LevelUpServiceClient;
import com.company.retailqueueconsumer.util.message.LevelUp;
import com.company.retailqueueconsumer.util.message.LevelUpViewModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    private final LevelUpServiceClient client;

    public MessageListener(LevelUpServiceClient client){
        this.client=client;
    }

    @RabbitListener(queues = RetailQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessage(LevelUp levelUp){
        if(levelUp.getLevelUpId()==0){
            System.out.println("Sending to Level up Service to create " + levelUp.toString());
            client.saveLevelUp(levelUp);
        } else{
            System.out.println("Sending to Level up Service to update " + levelUp.toString());
            client.updateLevelUp(levelUp.getLevelUpId(),levelUp);
        }
    }

    // Simulating Delay
    private void slowService() {
        try {
            long time = 1000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
