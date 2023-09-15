package com.cbs.middleware.web.rest.utility;

import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class NotificationEmailDTO {

    private String sender;

    private Set<String> receiver;

    private String content;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Set<String> getReceiver() {
        return receiver;
    }

    public void setReceiver(Set<String> receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
