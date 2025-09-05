package com.study.designpatterns.factoryMethod.documentProcessorImpl;

import com.study.designpatterns.factoryMethod.document.Document;
import com.study.designpatterns.factoryMethod.documentImpl.ExcelDocument;
import com.study.designpatterns.factoryMethod.documentProcessor.DocumentProcessor;

public class ExcelProcessor implements DocumentProcessor {
    @Override
    public Document createDocument(String fileName) {
        return new ExcelDocument(fileName);
    }
}
