package edu.metu.sucre.di.components;


import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import edu.metu.sucre.App;
import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.di.annotations.ApplicationContext;
import edu.metu.sucre.di.modules.ApplicationModule;

/**
 * Created by ilkay on 26/02/2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(App app);
    
    @ApplicationContext
    Context context();
    
    Application application();
    
    IDataManager getDataManager();
    
}
