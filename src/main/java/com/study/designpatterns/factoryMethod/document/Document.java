package com.study.designpatterns.factoryMethod.document;

public abstract class Document {
    private String fileName;

    protected Document(String fileName) {
        this.fileName = fileName;
    }

    public abstract void open();

    public abstract void save();

    public abstract void close();

    public abstract String getFormat();

    public String getFileName() {
        return fileName;
    }
}
