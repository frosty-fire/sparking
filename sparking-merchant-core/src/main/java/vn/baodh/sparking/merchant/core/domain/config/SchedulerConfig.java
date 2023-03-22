package vn.baodh.sparking.merchant.core.domain.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import vn.baodh.sparking.merchant.core.domain.model.UserResponse;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {

    private final SimpMessagingTemplate template;

//    @Scheduled(fixedDelay = 3000)
//    public void sendAdhocMessages() {
//        template.convertAndSend("/room/mock", new UserResponse().setContent("Fixed Delay Scheduler"));
//    }
}