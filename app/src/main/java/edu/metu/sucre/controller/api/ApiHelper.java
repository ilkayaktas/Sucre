package edu.metu.sucre.controller.api;

import android.net.Uri;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import edu.metu.sucre.controller.api.backend.BackendService;
import edu.metu.sucre.controller.api.fcm.FCMGroupService;
import edu.metu.sucre.model.api.Channel;
import edu.metu.sucre.model.api.FBUser;
import edu.metu.sucre.model.api.User;
import io.reactivex.Observable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Singleton;
import java.util.List;

/**
 * Created by ilkay on 12/03/2017.
 */

@Singleton
public class ApiHelper implements IApiHelper {
	private static final String ME_ENDPOINT = "/me";

	private BackendService backendService;
	private FCMGroupService fcmGroupService;

	public ApiHelper(BackendService backendService, FCMGroupService fcmGroupService) {
		this.backendService = backendService;
		this.fcmGroupService = fcmGroupService;
	}

	@Override
	public boolean login(User user, String userId, String token, String expireDate) {
		return false;
	}

	@Override
	public boolean logout(String userId, String token) {
		return false;
	}

	@Override
	public Observable<FBUser> getFacebookProfile() {
		return Observable.create(e -> {

			Bundle params = new Bundle();
			params.putString("fields", "picture,name,id,email,permissions");

			GraphRequest request = new GraphRequest(
					AccessToken.getCurrentAccessToken(),
					ME_ENDPOINT,
					params,
					HttpMethod.GET);
			GraphResponse response = request.executeAndWait();

			if(response.getConnection().getResponseCode() != 200){
				e.onError( new Throwable(response.getConnection().getResponseMessage()) );
			}else {
				e.onNext( jsonToUser( response.getJSONObject() ) );
				e.onComplete();
			}
		});
	}

	@Override
	public Observable<User> getUser(String userId) {
		return backendService.getUser(userId);
	}

	@Override
	public Observable<User> saveUser(User user) {
		return backendService.saveUser(user);
	}

	@Override
	public Observable<List<Channel>> getUserChannels(String userId, String fcmToken) {
		return backendService.getUserChannels(userId);
	}

	@Override
	public Observable<Channel> createChannel(Channel channel) {
		return backendService.createChannel(channel);
	}

	@Override
	public Observable<Channel> updateChannel(String id, String email) {
		return backendService.updateChannel(id, email);
	}

	@Override
	public Observable<Channel> getChannel(String channelId) {
		return backendService.getChannel(channelId);
	}

	private FBUser jsonToUser(JSONObject user) throws JSONException {
		Uri picture = Uri.parse(user.getJSONObject("picture").getJSONObject("data").getString("url"));
		String name = user.getString("name");
		String id = user.getString("id");
		String email = null;
		if (user.has("email")) {
			email = user.getString("email");
		}

		// Build permissions display string
		StringBuilder builder = new StringBuilder();
		JSONArray perms = user.getJSONObject("permissions").getJSONArray("data");
		builder.append("Permissions:\n");
		for (int i = 0; i < perms.length(); i++) {
			builder.append(perms.getJSONObject(i).get("permission")).append(": ").append(perms
					.getJSONObject(i).get("status")).append("\n");
		}
		String permissions = builder.toString();

		return new FBUser(picture, name, id, email, permissions);
	}
}
