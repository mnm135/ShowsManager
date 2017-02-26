package com.mnm135.emil.showsmanager;

public interface Presenter<V> {
    void attachView(V view);
    void detachView();
}
