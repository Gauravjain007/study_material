package com.study.designpatterns.factoryMethod.documentProcessorImpl;

import com.study.designpatterns.factoryMethod.document.Document;
import com.study.designpatterns.factoryMethod.documentImpl.WordDocument;
import com.study.designpatterns.factoryMethod.documentProcessor.DocumentProcessor;

public class WordProcessor implements DocumentProcessor {
    @Override
    public Document createDocument(String fileName) {
        return new WordDocument(fileName);
    }
}
