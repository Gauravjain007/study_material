package com.study.designpatterns.observer;

public interface Subject<T> {

    public void registerObserver(Observer<T> observer);

    public void removeObserver(Observer<T> observer);

    public void notifyObservers();

}
