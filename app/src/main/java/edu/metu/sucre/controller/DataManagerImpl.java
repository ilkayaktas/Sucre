package edu.metu.sucre.controller;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.metu.sucre.controller.api.ApiHelper;
import edu.metu.sucre.controller.db.DbHelper;
import edu.metu.sucre.controller.pref.PreferenceHelper;
import edu.metu.sucre.di.annotations.ApplicationContext;
import edu.metu.sucre.model.app.BloodSugar;

/**
 * Created by ilkay on 11/03/2017.
 */

@Singleton
public class DataManagerImpl implements DataManager{
	
	private final Context mContext;
	private final DbHelper mDbHelper;
	private final PreferenceHelper mPreferenceHelper;
	private final ApiHelper mApiHelper;
	
	@Inject
	public DataManagerImpl(@ApplicationContext Context mContext, DbHelper mDbHelper, PreferenceHelper mPreferenceHelper, ApiHelper mApiHelper) {
		this.mContext = mContext;
		this.mDbHelper = mDbHelper;
		this.mPreferenceHelper = mPreferenceHelper;
		this.mApiHelper = mApiHelper;
	}

	@Override
	public void initializeDatabase() {

	}

	@Override
	public List<BloodSugar> getBloodSugar() {
		return null;
	}

	@Override
	public void doApiCall() {

	}

	@Override
	public boolean getDatabaseCreatedStatus() {
		return false;
	}

	@Override
	public void setDatabaseCreatedStatus() {

	}
}
