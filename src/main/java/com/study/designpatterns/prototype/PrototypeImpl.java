package com.study.designpatterns.prototype;

import java.util.Date;
import java.util.ArrayDeque;

/**
 * Prototype Pattern implementation with memento
 */
class EditableDocument {
    private String content;
    private String title;
    private Date lastModified;

    public EditableDocument(String title, String content) {
        this.title = title;
        this.content = content;
        this.lastModified = new Date();
    }

    private EditableDocument(EditableDocument other) {
        this.title = other.title;
        this.content = other.content;
        this.lastModified = new Date(other.lastModified.getTime());
    }

    public EditableDocument copy() {
        return new EditableDocument(this);
    }

    // Memento creation
    public DocumentMemento createMemento() {
        return new DocumentMemento(copy());
    }

    // Restore from memento
    public void restoreFromMemento(DocumentMemento memento) {
        EditableDocument state = memento.getState();
        this.title = state.title;
        this.content = state.content;
        this.lastModified = new Date(state.lastModified.getTime());
    }

    // Document operations
    public void setContent(String content) {
        this.content = content;
        this.lastModified = new Date();
    }

    public void setTitle(String title) {
        this.title = title;
        this.lastModified = new Date();
    }

    // Getters...
    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public Date getLastModified() {
        return lastModified;
    }

    @Override
    public String toString() {
        return String.format(
                "EditableDocument { Document: %s, Title: %s, Last Modified: %s }",
                content, title, lastModified);
    }
}

// Memento class
class DocumentMemento {
    private final EditableDocument state;

    DocumentMemento(EditableDocument state) {
        this.state = state;
    }

    EditableDocument getState() {
        return state.copy(); // Return copy to maintain immutability
    }
}

// Document editor with undo/redo
class DocumentEditor {
    private EditableDocument document;
    private ArrayDeque<DocumentMemento> undoStack = new ArrayDeque<>();
    private ArrayDeque<DocumentMemento> redoStack = new ArrayDeque<>();

    public DocumentEditor(EditableDocument document) {
        this.document = document;
    }

    public void execute(DocumentCommand command) {
        // Save current state before executing command
        undoStack.push(document.createMemento());
        redoStack.clear(); // Clear redo stack when new operation is performed

        command.execute(document);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(document.createMemento());
            DocumentMemento memento = undoStack.pop();
            document.restoreFromMemento(memento);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(document.createMemento());
            DocumentMemento memento = redoStack.pop();
            document.restoreFromMemento(memento);
        }
    }
}

// Command interface for document operations
interface DocumentCommand {
    void execute(EditableDocument document);
}

// Concrete commands for document operations
class SetContentCommand implements DocumentCommand {
    private final String updatedContent;

    public SetContentCommand(String updatedContent) {
        this.updatedContent = updatedContent;
    }

    @Override
    public void execute(EditableDocument document) {
        document.setContent(updatedContent);
    }
}

public class PrototypeImpl {
    public static void main(String[] args) throws InterruptedException {
        EditableDocument document = new EditableDocument("My Document", "Initial content");
        DocumentEditor editor = new DocumentEditor(document);
        System.out.println("Original document:\n" + document);
        Thread.sleep(1000);
        editor.execute(new SetContentCommand("Updated content"));
        System.out.println("Updated document:\n" + document);
        Thread.sleep(600);
        editor.execute(new SetContentCommand("More updated content"));
        System.out.println("Updated document again:\n" + document);
        Thread.sleep(100);
        editor.undo();
        System.out.println("Document after undo:\n" + document);
        Thread.sleep(1000);
        editor.redo();
        System.out.println("Document after redo:\n" + document);
        Thread.sleep(500);
        editor.undo();
        System.out.println("Document after undo again:\n" + document);
        Thread.sleep(1200);
        editor.undo();
        System.out.println("Document after undo again:\n" + document);
    }
}
