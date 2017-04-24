package edu.metu.sucre.views.activities.splash;


import edu.metu.sucre.di.annotations.PerActivity;
import edu.metu.sucre.views.activities.base.MvpPresenter;

/**
 * Created by ilkay on 11/03/2017.
 */

@PerActivity
public interface SplashScreenMvpPresenter<V extends SplashScreenMvpView> extends MvpPresenter<V> {
	void createDatabase();
}
