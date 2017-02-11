package com.example.emil.showsmanager;

import android.app.Application;
import android.content.Context;

import com.example.emil.showsmanager.rest.TVMazeService;

import rx.Scheduler;
import rx.schedulers.Schedulers;


/**
 * Created by Emil on 11.02.2017.
 */

public class ShowsManagerApplication extends Application {

    private TVMazeService tvMazeService;
    private Scheduler defaultSubscribeScheduler;

    public static ShowsManagerApplication get(Context context) {
        return (ShowsManagerApplication) context.getApplicationContext();
    }
    public TVMazeService getTvMazeService() {
        if(tvMazeService == null) {
            tvMazeService = TVMazeService.Factory.create();
        }
        return tvMazeService;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }


}























