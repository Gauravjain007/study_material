package com.study.designpatterns.observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class Model {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String value;
    private int number;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void setValue(String newValue) {
        String oldValue = this.value;
        this.value = newValue;
        support.firePropertyChange("value", oldValue, newValue);
    }

    public void setNumber(int number) {
        int oldValue = this.number;
        this.number = number;
        support.firePropertyChange("number", oldValue, number);
    }

    public String getValue() {
        return value;
    }
}

public class PropertyListernerImpl {

    public static void main(String[] args) {
        // Usage
        Model model = new Model();
        model.setNumber(20); // No listeners yet, so no notification
        PropertyChangeListener listener = evt -> System.out.println(
                "Property " + evt.getPropertyName() + " changed from "
                        + evt.getOldValue() + " to " + evt.getNewValue());
        model.addPropertyChangeListener(listener);
        model.setValue("A");
        model.setNumber(100);
        model.setValue("B");
        model.removePropertyChangeListener(listener);
        model.setValue("C"); // No notification since listener is removed
    }
}
