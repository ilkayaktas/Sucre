package edu.metu.sucre.views.activities.channels;


import android.annotation.SuppressLint;
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

/**
 * Created by user on 02/08/2017.
 */

public class HealthChannelsPresenter<V extends HealthChannelsMvpView> extends BasePresenter<V>
		implements HealthChannelsMvpPresenter<V> {

	private DialogUser user;

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

    private void createDialog(Channel channel){
        System.out.println(channel.id + " "
                + channel.channelName + " "
                + channel.notificationKey + " "
                + channel.owner + " ");

	    String userID = getIDataManager().getUserId();
		user = new DialogUser(userID, "ilkay", "", true);
		Dialog dialog = new Dialog( channel.id,
                                    channel.channelName,
                                    AppConstants.GROUP_AVATAR,
                                    new ArrayList<>(Collections.singletonList(user)),
                                    new DialogMessage(String.valueOf(System.currentTimeMillis()), user, "messajjjjasdada"),
                                    79,
                                    channel.notificationKey);

	    getMvpView().onNewDialog(channel, dialog);
    }
}
