package com.zw.observer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class BabyBornEvent extends ApplicationEvent {
    @Getter @Setter
    private String babyName;
    @Getter @Setter
    private long bornDate;
    @Getter @Setter
    private String gender;

    public BabyBornEvent(Object source) {
        super(source);
    }

    public BabyBornEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
