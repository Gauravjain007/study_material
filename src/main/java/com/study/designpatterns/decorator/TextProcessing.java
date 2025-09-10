package com.study.designpatterns.decorator;

// Component interface
interface TextProcessor {
    String process(String text);
}

// ConcreteComponent
class PlainTextProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text;
    }
}

// Base Decorator
abstract class TextProcessorDecorator implements TextProcessor {
    protected TextProcessor processor;

    TextProcessorDecorator(TextProcessor processor) {
        this.processor = processor;
    }

    @Override
    public String process(String text) {
        return processor.process(text);
    }
}

// Concrete Decorators
class UpperCaseDecorator extends TextProcessorDecorator {
    public UpperCaseDecorator(TextProcessor processor) {
        super(processor);
    }

    @Override
    public String process(String text) {
        return processor.process(text).toUpperCase();
    }
}

class TrimDecorator extends TextProcessorDecorator {
    public TrimDecorator(TextProcessor processor) {
        super(processor);
    }

    @Override
    public String process(String text) {
        return processor.process(text).trim();
    }
}

class EncryptionDecorator extends TextProcessorDecorator {
    private final int shift;

    public EncryptionDecorator(TextProcessor processor, int shift) {
        super(processor);
        this.shift = shift;
    }

    @Override
    public String process(String text) {
        String processedText = processor.process(text);
        StringBuilder encrypted = new StringBuilder();

        for (char c : processedText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                encrypted.append((char) ((c - base + shift) % 26 + base));
            } else {
                encrypted.append(c);
            }
        }

        return encrypted.toString();
    }
}

// Usage
public class TextProcessing {
    public static void main(String[] args) {
        String input = "  Hello World!  ";

        TextProcessor processor = new PlainTextProcessor();
        processor = new TrimDecorator(processor);
        processor = new UpperCaseDecorator(processor);
        processor = new EncryptionDecorator(processor, 3);

        String result = processor.process(input);
        System.out.println("Original: '" + input + "'");
        System.out.println("Processed: '" + result + "'");
    }
}