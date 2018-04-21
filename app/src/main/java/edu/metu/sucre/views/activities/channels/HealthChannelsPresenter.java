package edu.metu.sucre.views.activities.channels;


import android.annotation.SuppressLint;
import android.util.Log;
import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.model.api.Channel;
import edu.metu.sucre.model.app.Dialog;
import edu.metu.sucre.model.app.DialogMessage;
import edu.metu.sucre.model.app.DialogUser;
import edu.metu.sucre.utils.AppConstants;
import edu.metu.sucre.views.activities.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by user on 02/08/2017.
 */

public class HealthChannelsPresenter<V extends HealthChannelsMvpView> extends BasePresenter<V>
		implements HealthChannelsMvpPresenter<V> {

	public HealthChannelsPresenter(IDataManager IDataManager) {
		super(IDataManager);
	}

	@SuppressLint("CheckResult")
    @Override
	public void addChannel(String channelName) {
        getIDataManager().createChannel(channelName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::createDialog, throwable -> onError("Group name exists!"));
    }

	@SuppressLint("CheckResult")
	@Override
	public void getUserChannels() {
		getIDataManager().getUserChannels()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(channels -> {
					for (Channel ch : channels) {
					    createDialog(ch);
					}
				}, throwable -> onError(throwable.getMessage()));
	}

	@Override
	public String getUserId() {
		return getIDataManager().getUserId();
	}

	private void onError(String message) {
	    getMvpView().showErrorToast(message);
    }

    @SuppressLint("CheckResult")
	private void createDialog(Channel channel){
        Log.d("_______IA_______", channel.toString());


        getIDataManager().getMe()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(usr ->{
					DialogUser user = new DialogUser(usr.userId, usr.name, usr.picture, true);
					Dialog dialog = new Dialog( channel.id,
							channel.channelName,
							AppConstants.GROUP_AVATAR,
							new ArrayList<>(Collections.singletonList(user)),
							new DialogMessage(UUID.randomUUID().toString(), user, "Health Channel is created!"),
							0,
							channel.notificationKey);

					getMvpView().onNewDialog(channel, dialog);
				}, throwable -> onError(throwable.getMessage()));



    }
}
