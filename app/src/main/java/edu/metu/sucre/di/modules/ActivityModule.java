package edu.metu.sucre.di.modules;

import android.app.Activity;
import android.graphics.Typeface;

import dagger.Module;
import dagger.Provides;
import edu.metu.sucre.controller.DataManager;
import edu.metu.sucre.controller.services.MobssAsyncTask;
import edu.metu.sucre.controller.strategy.Strategy;
import edu.metu.sucre.di.annotations.PerActivity;
import edu.metu.sucre.views.activities.home.MainMvpPresenter;
import edu.metu.sucre.views.activities.home.MainMvpView;
import edu.metu.sucre.views.activities.home.MainPresenter;
import edu.metu.sucre.views.activities.splash.SplashScreenMvpPresenter;
import edu.metu.sucre.views.activities.splash.SplashScreenMvpView;
import edu.metu.sucre.views.activities.splash.SplashScreenPresenter;

/**
 * Created by ilkay on 10/03/2017.
 */

@Module
public class ActivityModule {
    Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity(){
        return activity;
    }
    
    @Provides
    @PerActivity
    Typeface provideTypeface(){
        return Typeface.createFromAsset(activity.getAssets(), "gothic.TTF");
    }
    
    @Provides
    @PerActivity
    SplashScreenMvpPresenter<SplashScreenMvpView> provideSplashScreenPresenter(DataManager dataManager) {
        return new SplashScreenPresenter<SplashScreenMvpView>(dataManager);
    }
    
    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> providesMainPresenter(DataManager dataManager){
        return new MainPresenter<MainMvpView>(dataManager);
    }
    
    @Provides
    @PerActivity
    MobssAsyncTask providesMobssAsyncTask(Activity activity, Strategy strategy){
        return new MobssAsyncTask(activity, strategy);
    }
    
}
