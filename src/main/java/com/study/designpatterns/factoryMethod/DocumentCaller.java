package com.study.designpatterns.factoryMethod;

import com.study.designpatterns.factoryMethod.documentProcessor.DocumentProcessor;
import com.study.designpatterns.factoryMethod.documentProcessorImpl.ExcelProcessor;
import com.study.designpatterns.factoryMethod.documentProcessorImpl.PDFProcessor;
import com.study.designpatterns.factoryMethod.documentProcessorImpl.WordProcessor;

public class DocumentCaller {
    public static void main(String[] args) {
        DocumentProcessor excelProcessor = new ExcelProcessor();
        excelProcessor.processDocument("report.xlsx");

        System.out.println();

        DocumentProcessor wordProcessor = new WordProcessor();
        wordProcessor.processDocument("thesis.docx");

        System.out.println();

        DocumentProcessor pdfProcessor = new PDFProcessor();
        pdfProcessor.processDocument("ebook.pdf");
    }
}
