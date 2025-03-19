package com.study.designpatterns.dependency_injection;

import java.util.ServiceLoader;

import com.study.designpatterns.dependency_injection.interfaces.MessageService;
import com.study.designpatterns.dependency_injection.managers.AlertManager;
import com.study.designpatterns.dependency_injection.managers.NotificationManager;
import com.study.designpatterns.dependency_injection.managers.ReminderManager;
import com.study.designpatterns.dependency_injection.services.EmailService;
import com.study.designpatterns.dependency_injection.services.SMSService;

public class MessagingServiceContainer {
    /**
     * <h3>Dependency Injection</h3>
     * A demo of the dependency injection pattern, where the object is
     * given its dependencies rather than creating them itself.
     * <p>
     * The demo shows how to use the same service ({@link NotificationManager})
     * with different implementations of the message service
     * ({@link SMSService} and {@link EmailService}).
     * Demonstrates {@code Constructor Injection}
     * <p>
     * The demo also shows how to use the same service ({@link AlertManager})
     * with different implementations of the message service
     * ({@link SMSService} and {@link EmailService}).
     * Demonstrates {@code Setter Injection}
     * <p>
     * The demo also shows how to use the same service ({@link ReminderManager})
     * with different implementations of the message service
     * ({@link SMSService} and {@link EmailService}).
     * Demonstrates {@code Method/Interface Injection}
     * <p>
     * The demo also shows how to use the ServiceLoader to load all available
     * implementations of the {@link MessageService} interface.
     * Demonstrates Java's {@code ServiceLoader} (built-in DI mechanism)
     * <p>
     * The advantage of this approach is that the service does not need to know
     * how to create the message service, which makes the service more
     * independent and easier to test.
     */
    public static void main(String[] args) {

        // Constructor injection
        MessageService messageService = new SMSService();
        NotificationManager notificationService = new NotificationManager(messageService);
        notificationService.notifyUser("John");

        messageService = new EmailService();
        notificationService = new NotificationManager(messageService);
        notificationService.notifyUser("Jacob");

        // Setter injection
        AlertManager alertManager = new AlertManager();
        alertManager.setMessageService(messageService);
        alertManager.alertUser("Jack");

        messageService = new SMSService();
        alertManager.setMessageService(messageService);
        alertManager.alertUser("Jonas");

        // Method/Interface injection
        ReminderManager reminderService = new ReminderManager();
        reminderService.injectService(messageService);
        reminderService.sendReminder("Jill");

        messageService = new EmailService();
        reminderService.injectService(messageService);
        reminderService.sendReminder("Joe");

        /**
         * ServiceLoader
         * Loads all available implementations of the MessageService interface and calls
         * the getMessage() method on each one.
         * !Note: Requires to create
         * !src/main/META-INF/services/<PackageName.InterfaceName> with the fully
         * !qualified class name of the implementations
         */
        ServiceLoader<MessageService> loader = ServiceLoader.load(MessageService.class);
        for (MessageService service : loader) {
            System.out.println("Found service: " + service.getMessage("Gaurav"));
        }
    }
}
