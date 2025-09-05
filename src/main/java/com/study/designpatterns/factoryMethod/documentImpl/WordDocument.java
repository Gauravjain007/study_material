package com.study.designpatterns.factoryMethod.documentImpl;

import com.study.designpatterns.factoryMethod.document.Document;

public class WordDocument extends Document {
    public WordDocument(String fileName) {
        super(fileName);
    }

    @Override
    public void open() {
        System.out.println("Opening WORD document: " + getFileName());
    }

    @Override
    public void save() {
        System.out.println("Saving WORD document: " + getFileName());
    }

    @Override
    public void close() {
        System.out.println("Closing WORD document: " + getFileName());
    }

    @Override
    public String getFormat() {
        return "DOCX";
    }
}
