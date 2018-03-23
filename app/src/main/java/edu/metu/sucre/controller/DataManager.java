package edu.metu.sucre.controller;

import android.content.Context;
import edu.metu.sucre.controller.api.IApiHelper;
import edu.metu.sucre.controller.db.IDbHelper;
import edu.metu.sucre.controller.pref.IPreferenceHelper;
import edu.metu.sucre.di.annotations.ApplicationContext;
import edu.metu.sucre.model.api.User;
import edu.metu.sucre.model.app.BloodSugar;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * Created by ilkay on 11/03/2017.
 */

@Singleton
public class DataManager implements IDataManager {
	
	private final Context mContext;
	private final IDbHelper mIDbHelper;
	private final IPreferenceHelper mIPreferenceHelper;
	private final IApiHelper mIApiHelper;
	
	@Inject
	public DataManager(@ApplicationContext Context mContext, IDbHelper mIDbHelper, IPreferenceHelper mIPreferenceHelper, IApiHelper mIApiHelper) {
		this.mContext = mContext;
		this.mIDbHelper = mIDbHelper;
		this.mIPreferenceHelper = mIPreferenceHelper;
		this.mIApiHelper = mIApiHelper;
	}


	@Override
	public List<BloodSugar> getBloodSugar() {
		return mIDbHelper.getBloodSugar();
	}

	@Override
	public void saveBloodSugar(BloodSugar bloodSugar) {
		mIDbHelper.saveBloodSugar(bloodSugar);
	}
	
	@Override
	public void deleteBloodSugar(String uuid) {
		mIDbHelper.deleteBloodSugar(uuid);
	}
	
	@Override
	public boolean getDatabaseCreatedStatus() {
		return false;
	}

	@Override
	public void setDatabaseCreatedStatus() {

	}

	@Override
	public Observable<User> getFacebookProfile() {
		return mIApiHelper.getFacebookProfile();
	}
}
