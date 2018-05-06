package edu.metu.sucre;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.google.firebase.FirebaseApp;
import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.di.components.ApplicationComponent;
import edu.metu.sucre.di.components.DaggerApplicationComponent;
import edu.metu.sucre.di.modules.ApplicationModule;
import io.realm.Realm;

import javax.inject.Inject;

/**
 * Created by iaktas on 24.04.2017.
 */

public class App extends Application {

    ApplicationComponent appComponent;

    @Inject
    IDataManager mIDataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);

        Realm.init(this);

        initializeInjector();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
