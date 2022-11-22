package com.zw.service;

import com.zw.observer.BabyBornEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PartyService implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof BabyBornEvent) {
            BabyBornEvent babyBornEvent = (BabyBornEvent) event;
            log.info("Oh, congratulations, a new baby is born, name: {}, at: {}, gender: {}",
                    babyBornEvent.getBabyName(), babyBornEvent.getBornDate(), babyBornEvent.getGender());
            log.info("We should hold a party to celebrate with all relatives and friends.");
        }
    }
}
