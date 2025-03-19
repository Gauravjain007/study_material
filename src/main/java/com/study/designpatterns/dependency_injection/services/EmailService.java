package com.study.designpatterns.dependency_injection.services;

import com.study.designpatterns.dependency_injection.interfaces.MessageService;

public class EmailService implements MessageService {
    @Override
    public String getMessage(String rec) {
        return "Email sent successfully to: " + rec;
    }
}
