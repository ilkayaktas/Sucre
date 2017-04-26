package edu.metu.sucre.di.modules;

import android.app.Activity;
import android.graphics.Typeface;

import dagger.Module;
import dagger.Provides;
import edu.metu.sucre.controller.DataManager;
import edu.metu.sucre.controller.services.MobssAsyncTask;
import edu.metu.sucre.controller.strategy.Strategy;
import edu.metu.sucre.di.annotations.ActivityContext;
import edu.metu.sucre.di.annotations.PerActivity;
import edu.metu.sucre.views.activities.home.MainMvpPresenter;
import edu.metu.sucre.views.activities.home.MainMvpView;
import edu.metu.sucre.views.activities.home.MainPresenter;
import edu.metu.sucre.views.activities.splash.SplashScreenMvpPresenter;
import edu.metu.sucre.views.activities.splash.SplashScreenMvpView;
import edu.metu.sucre.views.activities.splash.SplashScreenPresenter;
import edu.metu.sucre.views.activities.sugarlevel.SugarLevelMvpPresenter;
import edu.metu.sucre.views.activities.sugarlevel.SugarLevelMvpView;
import edu.metu.sucre.views.activities.sugarlevel.SugarLevelPresenter;

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
        return Typeface.createFromAsset(activity.getAssets(), "fonts/Sketch.ttf");
    }
    
    @Provides
    @PerActivity
    @ActivityContext
    Typeface provideTypefaceForActivity(){
        return Typeface.createFromAsset(activity.getAssets(), "fonts/gothic.TTF");
    }
    
    @Provides
    @PerActivity
    SplashScreenMvpPresenter<SplashScreenMvpView> provideSplashScreenPresenter(DataManager dataManager) {
        return new SplashScreenPresenter<>(dataManager);
    }
    
    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> providesMainPresenter(DataManager dataManager){
        return new MainPresenter<>(dataManager);
    }
    
    @Provides
    @PerActivity
    SugarLevelMvpPresenter<SugarLevelMvpView> providesSugarLevelPresenter(DataManager dataManager){
        return new SugarLevelPresenter<>(dataManager);
    }
    
    @Provides
    @PerActivity
    MobssAsyncTask providesMobssAsyncTask(Activity activity, Strategy strategy){
        return new MobssAsyncTask(activity, strategy);
    }
    
}
