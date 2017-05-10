package edu.metu.sucre.views.activities.splash;


import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.views.activities.base.BasePresenter;

/**
 * Created by ilkay on 11/03/2017.
 */

public class SplashScreenPresenter <V extends SplashScreenMvpView> extends BasePresenter<V>
		implements SplashScreenMvpPresenter<V>{
	
	public SplashScreenPresenter(IDataManager IDataManager) {
		super(IDataManager);
	}
	
	@Override
	public void createDatabase() {
		if( getIDataManager().getDatabaseCreatedStatus() ){
			// start main activity
			getMvpView().openMainActivity();
			
		} else{
			// initialize database. Read asset folder
			getIDataManager().initializeDatabase();
			
			// create db
			getIDataManager().setDatabaseCreatedStatus();
			
			// start main activity
			getMvpView().openMainActivity();
		}
	}
}
