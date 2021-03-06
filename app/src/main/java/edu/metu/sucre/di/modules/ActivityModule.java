package edu.metu.sucre.di.modules;

import android.app.Activity;
import android.graphics.Typeface;
import dagger.Module;
import dagger.Provides;
import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.controller.services.MobssAsyncTask;
import edu.metu.sucre.controller.strategy.Strategy;
import edu.metu.sucre.di.annotations.ActivityContext;
import edu.metu.sucre.di.annotations.PerActivity;
import edu.metu.sucre.views.activities.channels.HealthChannelsMvpPresenter;
import edu.metu.sucre.views.activities.channels.HealthChannelsMvpView;
import edu.metu.sucre.views.activities.channels.HealthChannelsPresenter;
import edu.metu.sucre.views.activities.healthdatalist.HealthDataListMvpPresenter;
import edu.metu.sucre.views.activities.healthdatalist.HealthDataListMvpView;
import edu.metu.sucre.views.activities.healthdatalist.HealthDataListPresenter;
import edu.metu.sucre.views.activities.home.MainMvpPresenter;
import edu.metu.sucre.views.activities.home.MainMvpView;
import edu.metu.sucre.views.activities.home.MainPresenter;
import edu.metu.sucre.views.activities.login.LoginMvpPresenter;
import edu.metu.sucre.views.activities.login.LoginMvpView;
import edu.metu.sucre.views.activities.login.LoginPresenter;
import edu.metu.sucre.views.activities.messages.HealthChannelMessageMvpPresenter;
import edu.metu.sucre.views.activities.messages.HealthChannelMessageMvpView;
import edu.metu.sucre.views.activities.messages.HealthChannelMessagePresenter;
import edu.metu.sucre.views.activities.splash.SplashScreenMvpPresenter;
import edu.metu.sucre.views.activities.splash.SplashScreenMvpView;
import edu.metu.sucre.views.activities.splash.SplashScreenPresenter;
import edu.metu.sucre.views.activities.sugarlevel.SugarLevelMvpPresenter;
import edu.metu.sucre.views.activities.sugarlevel.SugarLevelMvpView;
import edu.metu.sucre.views.activities.sugarlevel.SugarLevelPresenter;
import edu.metu.sucre.views.fragments.listfragment.ListMvpPresenter;
import edu.metu.sucre.views.fragments.listfragment.ListMvpView;
import edu.metu.sucre.views.fragments.listfragment.ListPresenter;
import edu.metu.sucre.views.fragments.statisticsfragment.StatisticsMvpPresenter;
import edu.metu.sucre.views.fragments.statisticsfragment.StatisticsMvpView;
import edu.metu.sucre.views.fragments.statisticsfragment.StatisticsPresenter;

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
    SplashScreenMvpPresenter<SplashScreenMvpView> provideSplashScreenPresenter(IDataManager IDataManager) {
        return new SplashScreenPresenter<>(IDataManager);
    }
    
    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> providesMainPresenter(IDataManager IDataManager){
        return new MainPresenter<>(IDataManager);
    }
    
    @Provides
    @PerActivity
    SugarLevelMvpPresenter<SugarLevelMvpView> providesSugarLevelPresenter(IDataManager IDataManager){
        return new SugarLevelPresenter<>(IDataManager);
    }
    
    @Provides
    @PerActivity
    MobssAsyncTask providesMobssAsyncTask(Activity activity, Strategy strategy){
        return new MobssAsyncTask(activity, strategy);
    }

    @Provides
    @PerActivity
    ListMvpPresenter<ListMvpView> providesListMvpPresenter(IDataManager IDataManager){
        return new ListPresenter<>(IDataManager);
    }

    @Provides
    @PerActivity
    StatisticsMvpPresenter<StatisticsMvpView> providesStatisticsMvpPresenter(IDataManager IDataManager){
        return new StatisticsPresenter<>(IDataManager);
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> providesLoginMvpPresenter(IDataManager IDataManager){
        return new LoginPresenter<>(IDataManager);
    }


    @Provides
    @PerActivity
    HealthChannelsMvpPresenter<HealthChannelsMvpView> providesHealthChannelsMvpPresenter(IDataManager IDataManager){
        return new HealthChannelsPresenter<>(IDataManager);
    }

    @Provides
    @PerActivity
    HealthChannelMessageMvpPresenter<HealthChannelMessageMvpView> providesHealthChannelMessageMvpPresenter(IDataManager IDataManager){
        return new HealthChannelMessagePresenter<>(IDataManager);
    }

    @Provides
    @PerActivity
    HealthDataListMvpPresenter<HealthDataListMvpView> providesHealthDataListMvpPresenter(IDataManager IDataManager){
        return new HealthDataListPresenter<>(IDataManager);
    }

}
