package com.example.emil.showsmanager.presenter;

public interface Presenter<V> {
    void attachView(V view);
    void detachView();
}
