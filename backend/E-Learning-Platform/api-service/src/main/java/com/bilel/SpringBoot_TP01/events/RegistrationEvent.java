package com.bilel.SpringBoot_TP01.events;
import com.bilel.SpringBoot_TP01.entities.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RegistrationEvent extends ApplicationEvent {
    private final User user;
    private final String applicationURL;

    public RegistrationEvent(User user, String applicationURL) {
        super(user);
        this.user = user;
        this.applicationURL = applicationURL;
    }
}

