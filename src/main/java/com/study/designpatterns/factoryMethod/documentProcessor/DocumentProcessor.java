package com.study.designpatterns.factoryMethod.documentProcessor;

import com.study.designpatterns.factoryMethod.document.Document;

public interface DocumentProcessor {
    public Document createDocument(String fileName);

    public default void processDocument(String fileName) {
        System.out.println("Processing document: " + fileName);
        Document doc = createDocument(fileName);
        System.out.println("Document created with format: " + doc.getFormat());
        doc.open();
        doc.save();
        doc.close();
    }
}
