package com.study.designpatterns.factoryMethod.documentImpl;

import com.study.designpatterns.factoryMethod.document.Document;

public class ExcelDocument extends Document {
    public ExcelDocument(String fileName) {
        super(fileName);
    }

    @Override
    public void open() {
        System.out.println("Opening EXCEL document: " + getFileName());
    }

    @Override
    public void save() {
        System.out.println("Saving EXCEL document: " + getFileName());
    }

    @Override
    public void close() {
        System.out.println("Closing EXCEL document: " + getFileName());
    }

    @Override
    public String getFormat() {
        return "XLSX";
    }
}
