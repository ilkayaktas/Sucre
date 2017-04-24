package edu.metu.sucre.views.activities.splash;


import edu.metu.sucre.controller.DataManager;
import edu.metu.sucre.views.activities.base.BasePresenter;

/**
 * Created by ilkay on 11/03/2017.
 */

public class SplashScreenPresenter <V extends SplashScreenMvpView> extends BasePresenter<V>
		implements SplashScreenMvpPresenter<V>{
	
	public SplashScreenPresenter(DataManager dataManager) {
		super(dataManager);
	}
	
	@Override
	public void createDatabase() {
		if( getDataManager().getDatabaseCreatedStatus() ){
			// start main activity
			getMvpView().openMainActivity();
			
		} else{
			// initialize database. Read asset folder
			getDataManager().initializeDatabase();
			
			// create db
			getDataManager().setDatabaseCreatedStatus();
			
			// start main activity
			getMvpView().openMainActivity();
		}
	}
}
