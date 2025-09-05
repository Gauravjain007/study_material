package com.study.designpatterns.factoryMethod.documentProcessorImpl;

import com.study.designpatterns.factoryMethod.document.Document;
import com.study.designpatterns.factoryMethod.documentImpl.PDFDocument;
import com.study.designpatterns.factoryMethod.documentProcessor.DocumentProcessor;

public class PDFProcessor implements DocumentProcessor {
    @Override
    public Document createDocument(String fileName) {
        return new PDFDocument(fileName);
    }

    @Override
    public void processDocument(String fileName) {
        System.out.println("[OVERRIDDEN METHOD]");
        System.out.println("Starting PDF document processing...");
        Document doc = createDocument(fileName);
        System.out.println("Document created with format: " + doc.getFormat());
        doc.open();
        doc.save();
        doc.close();
        System.out.println("Finished PDF document processing.");
    }
}
