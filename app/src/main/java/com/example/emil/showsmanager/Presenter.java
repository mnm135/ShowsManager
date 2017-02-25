package com.example.emil.showsmanager;

public interface Presenter<V> {
    void attachView(V view);
    void detachView();
}
