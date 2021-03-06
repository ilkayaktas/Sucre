package edu.metu.sucre.controller;

import android.content.Context;
import com.facebook.AccessToken;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import edu.metu.sucre.controller.api.IApiHelper;
import edu.metu.sucre.controller.db.IDbHelper;
import edu.metu.sucre.controller.pref.IPreferenceHelper;
import edu.metu.sucre.di.annotations.ApplicationContext;
import edu.metu.sucre.model.api.*;
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
	
	private final Context context;
	private final IDbHelper dbHelper;
	private final IPreferenceHelper preferenceHelper;
	private final IApiHelper apiHelper;
	
	@Inject
	public DataManager(@ApplicationContext Context context, IDbHelper dbHelper, IPreferenceHelper preferenceHelper, IApiHelper apiHelper) {
		this.context = context;
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
	public Observable<User> saveUser(User user) {
		return apiHelper.saveUser(user);
	}

	@Override
	public Observable<Channel> addUserToChannel(String dialogId, String email) {
		return apiHelper.updateChannel(dialogId, email);
	}

	@Override
	public Observable<Channel> getChannel(String channelId) {
		return apiHelper.getChannel(channelId);
	}

	@Override
	public Observable<User> getUser(String userId) {
		return apiHelper.getUser(userId);
	}

	@Override
	public Observable<Void> sendMessage(Message message) {
		return apiHelper.sendMessage(message);
	}

	@Override
	public Observable<List<Message>> getMessages(String channelId) {
		return apiHelper.getMessages(channelId);
	}

	@Override
	public Observable<HealthData> saveHealthData(HealthData healthData) {
		return apiHelper.saveHealthData(healthData);
	}

	@Override
	public Observable<List<HealthData>> getHealthData(String userId, String healthDataTypeId) {
		return apiHelper.getHealthData(userId, healthDataTypeId);
	}

	@Override
	public Observable<BloodSugarData> saveBloodSugarToServer(BloodSugarData bloodSugar) {
		return apiHelper.saveBloodSugar(bloodSugar);
	}

	@Override
	public Observable<List<BloodSugarData>> getBloodSugarFromServer(String userId, String sugarMeasurementType) {
		return apiHelper.getBloodSugar(userId, sugarMeasurementType);
	}

	@Override
	public Observable<BloodSugarData> deleteBloodSugarFromServer(String bloodSugarId) {
		return apiHelper.deleteBloodSugar(bloodSugarId);
	}

	@Override
	public Observable<FBUser> getFacebookProfile() {
		return apiHelper.getFacebookProfile();
	}

	@Override
	public String getFCMToken() {
		return FirebaseInstanceId.getInstance().getToken();
	}

	@Override
	public String getUserId() {
		AccessToken facebookToken = AccessToken.getCurrentAccessToken();

		if(facebookToken != null){
			return facebookToken.getUserId();
		}
		return null;
	}

	@Override
	public Observable<User> getMe(){
		String userID = getUserId();
		return getUser(userID);
	}
	@Override
	public void subscribeToTopic(String topic) {
		FirebaseMessaging.getInstance().subscribeToTopic(topic);
	}

	@Override
	public Observable<Channel>  createChannel(String channelName) {
		String userId = getUserId();

		if(userId != null){
			Channel channel = new Channel();
			channel.owner = userId;
			channel.channelName = channelName;
			channel.guestUserIds.add(userId);
			return apiHelper.createChannel(channel);
		} else{
		    throw new IllegalArgumentException("Error on Facebook login or Firebase Cloud Messaging!");
        }
	}

	@Override
	public Observable<List<Channel>> getUserChannels() {
		String fcmToken = getFCMToken();
		String userId = getUserId();

		return apiHelper.getUserChannels(userId, fcmToken);
	}
}
