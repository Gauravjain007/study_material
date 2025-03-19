package com.study.designpatterns.dependency_injection.managers;

import com.study.designpatterns.dependency_injection.interfaces.MessageService;

public class AlertManager {
    private MessageService messageService;

    /**
     * Sets the MessageService instance used by this AlertService.
     * Illustrates {@code Setter Injection}
     *
     * @param messageService the MessageService instance to be injected
     */
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Alerts the user with a message constructed by the injected MessageService.
     *
     * @param rec the recipient of the message
     * @throws IllegalStateException if {@link #setMessageService(MessageService)}
     *                               has not been invoked
     */
    public void alertUser(String rec) {
        if (messageService == null) {
            throw new IllegalStateException("MessageService is not injected");
        }
        System.out.println("ALERT: " + messageService.getMessage(rec));
    }
}
