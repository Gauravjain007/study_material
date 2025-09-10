package com.study.designpatterns.decorator;

interface Component {
    String process(String text);
}

class ConcreteComponent implements Component {
    @Override
    public String process(String text) {
        System.out.println("Processing text: " + text);
        return text;
    }
}

class Decorator implements Component {
    protected Component component;
    protected String text = "";

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public String process(String text) {
        return component.process(text);
    }
}

class LoggingDecorator extends Decorator {
    public LoggingDecorator(Component component) {
        super(component);
    }

    @Override
    public String process(String text) {
        return "[LOGGED] " + super.process(text);
    }
}

class TimingDecorator extends Decorator {
    public TimingDecorator(Component component) {
        super(component);
    }

    @Override
    public String process(String text) {
        return "[" + System.currentTimeMillis() + "] " + super.process(text);
    }
}

class CachingDecorator extends Decorator {
    private String cachedResult;

    public CachingDecorator(Component component) {
        super(component);
    }

    @Override
    public String process(String text) {
        if (cachedResult == null) {
            cachedResult = super.process(text);
            return "[ADDED TO CACHE] " + cachedResult;
        } else {
            return "[FROM CACHE] " + cachedResult;
        }
    }
}

class ComponentBuilder {

    private Component component;

    public ComponentBuilder(Component base) {
        this.component = base;
    }

    public ComponentBuilder withLogging() {
        this.component = new LoggingDecorator(this.component);
        return this;
    }

    public ComponentBuilder withTiming() {
        this.component = new TimingDecorator(this.component);
        return this;
    }

    public ComponentBuilder withCaching() {
        this.component = new CachingDecorator(this.component);
        return this;
    }

    public Component build() {
        return this.component;
    }
}

public class DecoratorBuilder {

    public static void main(String[] args) {
        // Usage
        Component component = new ComponentBuilder(new ConcreteComponent())
                .withLogging()
                .withTiming()
                .withCaching()
                .build();
        System.out.println(component.process("Hello, world!"));
    }
}
