package com.study.designpatterns.dependency_injection.managers;

import com.study.designpatterns.dependency_injection.interfaces.MessageService;
import com.study.designpatterns.dependency_injection.interfaces.ServiceInjector;

public class ReminderManager implements ServiceInjector {
    private MessageService messageService;

    /**
     * Injects the MessageService to be used by this ReminderManager instance.
     * This method is a part of the ServiceInjector interface and is used to
     * inject the service which is used to construct the message content.
     * <p>
     * Demonstrates {@code Method/Interface Injection}
     * 
     * @param messageService the MessageService to be injected
     */
    @Override
    public void injectService(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Sends a reminder message to the specified recipient using the injected
     * MessageService.
     *
     * @param rec the recipient of the reminder message
     * @throws IllegalStateException if {@link #injectService(MessageService)}
     *                               has not been invoked
     */
    public void sendReminder(String rec) {
        if (messageService == null) {
            throw new IllegalStateException("MessageService is not injected");
        }
        System.out.println("REMINDER: " + messageService.getMessage(rec));
    }
}
