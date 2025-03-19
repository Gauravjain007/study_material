package com.study.designpatterns.dependency_injection.managers;

import com.study.designpatterns.dependency_injection.interfaces.MessageService;

public class NotificationManager {
    private final MessageService messageService;

    /**
     * Constructs a NotificationService with the given MessageService.
     * Illustrates {@code Constructor Injection}
     * 
     * @param messageService
     */
    public NotificationManager(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Notifies the user by printing a message constructed by the injected
     * MessageService.
     *
     * @param rec the recipient of the message
     */
    public void notifyUser(String rec) {
        System.out.println(messageService.getMessage(rec));
    }
}
