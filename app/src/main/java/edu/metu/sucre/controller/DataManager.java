package edu.metu.sucre.controller;

import android.content.Context;
import edu.metu.sucre.controller.api.IApiHelper;
import edu.metu.sucre.controller.db.IDbHelper;
import edu.metu.sucre.controller.pref.IPreferenceHelper;
import edu.metu.sucre.di.annotations.ApplicationContext;
import edu.metu.sucre.model.api.Channel;
import edu.metu.sucre.model.api.FBUser;
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
	private final IDbHelper dbHelper;
	private final IPreferenceHelper preferenceHelper;
	private final IApiHelper apiHelper;
	
	@Inject
	public DataManager(@ApplicationContext Context mContext, IDbHelper dbHelper, IPreferenceHelper preferenceHelper, IApiHelper apiHelper) {
		this.mContext = mContext;
		this.dbHelper = dbHelper;
		this.preferenceHelper = preferenceHelper;
		this.apiHelper = apiHelper;
	}


	@Override
	public List<BloodSugar> getBloodSugar() {
		return dbHelper.getBloodSugar();
	}

	@Override
	public void saveBloodSugar(BloodSugar bloodSugar) {
		dbHelper.saveBloodSugar(bloodSugar);
	}
	
	@Override
	public void deleteBloodSugar(String uuid) {
		dbHelper.deleteBloodSugar(uuid);
	}
	
	@Override
	public boolean getDatabaseCreatedStatus() {
		return false;
	}

	@Override
	public void setDatabaseCreatedStatus() {

	}

	@Override
	public Observable<FBUser> getFacebookProfile() {
		return apiHelper.getFacebookProfile();
	}

	@Override
	public Observable<User> getUser(String userId) {
		return apiHelper.getUser(userId);
	}

	@Override
	public Observable<User> addUser(User user) {
		return apiHelper.addUser(user);
	}

	@Override
	public Observable<Channel> getUserChannels(String userId) {
		return apiHelper.getUserChannels(userId);
	}

	@Override
	public Observable<Channel> addChannel(Channel channel) {
		return apiHelper.addChannel(channel);
	}

	@Override
	public Observable<Channel> updateChannel(String id, String memberToken) {
		return apiHelper.updateChannel(id, memberToken);
	}
}
