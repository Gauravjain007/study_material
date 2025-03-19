package com.study.designpatterns.dependency_injection.interfaces;

/**
 * Marker interface for dependency injection
 * Demonstrates {@code Interface Injection}
 */
public interface ServiceInjector {
    void injectService(MessageService messageService);
}
