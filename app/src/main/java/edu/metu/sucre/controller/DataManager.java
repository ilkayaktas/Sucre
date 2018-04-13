package edu.metu.sucre.controller;

import android.content.Context;
import com.facebook.AccessToken;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import edu.metu.sucre.controller.api.IApiHelper;
import edu.metu.sucre.controller.db.IDbHelper;
import edu.metu.sucre.controller.pref.IPreferenceHelper;
import edu.metu.sucre.di.annotations.ApplicationContext;
import edu.metu.sucre.model.api.Channel;
import edu.metu.sucre.model.api.FBUser;
import edu.metu.sucre.model.app.BloodSugar;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
	public Observable<FBUser> getFacebookProfile() {
		return apiHelper.getFacebookProfile();
	}

	@Override
	public String getFCMToken() {
		return FirebaseInstanceId.getInstance().getToken();
	}

	@Override
	public AccessToken getFacebookToken() {
		return AccessToken.getCurrentAccessToken();
	}

	@Override
	public void subscribeToTopic(String topic) {
		FirebaseMessaging.getInstance().subscribeToTopic(topic);
	}

	@Override
	public void createChannel(String channelName) {
		String fcmToken = getFCMToken();

		apiHelper.createFCMGroup(channelName, fcmToken)
				.observeOn(Schedulers.io())
				.subscribeOn(AndroidSchedulers.mainThread())
				.subscribe(notificationKey -> System.out.println(notificationKey));

		AccessToken facebookToken = getFacebookToken();

		if(fcmToken != null && facebookToken != null){
			Channel channel = new Channel();
			channel.owner = facebookToken.getUserId();
			channel.channelName = channelName;
			channel.members.add(fcmToken);
			//apiHelper.addChannel(channelName, token);
		}
	}
}
