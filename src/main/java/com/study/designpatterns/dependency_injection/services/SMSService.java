package com.study.designpatterns.dependency_injection.services;

import com.study.designpatterns.dependency_injection.interfaces.MessageService;

public class SMSService implements MessageService {
    @Override
    public String getMessage(String rec) {
        return "SMS sent successfully to: " + rec;
    }
}
