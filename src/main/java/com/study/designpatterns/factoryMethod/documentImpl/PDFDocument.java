package com.study.designpatterns.factoryMethod.documentImpl;

import com.study.designpatterns.factoryMethod.document.Document;

public class PDFDocument extends Document {

    public PDFDocument(String fileName) {
        super(fileName);
    }

    @Override
    public void open() {
        System.out.println("Opening PDF document: " + getFileName());
    }

    @Override
    public void save() {
        System.out.println("Saving PDF document: " + getFileName());
    }

    @Override
    public void close() {
        System.out.println("Closing PDF document: " + getFileName());
    }

    @Override
    public String getFormat() {
        return "PDF";
    }
}
