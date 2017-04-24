package edu.metu.sucre;

import android.app.Application;

import javax.inject.Inject;

import edu.metu.sucre.controller.DataManager;
import edu.metu.sucre.di.components.ApplicationComponent;
import edu.metu.sucre.di.components.DaggerApplicationComponent;
import edu.metu.sucre.di.modules.ApplicationModule;
import io.realm.Realm;

/**
 * Created by iaktas on 24.04.2017.
 */

public class App extends Application {

    ApplicationComponent appComponent;

    @Inject
    DataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        initializeInjector();

    }

    private void initializeInjector(){
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        appComponent.inject(this);

    }

    public ApplicationComponent getAppComponent(){
        return appComponent;
    }

}
