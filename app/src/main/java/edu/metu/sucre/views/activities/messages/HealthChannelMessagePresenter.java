package edu.metu.sucre.views.activities.messages;


import android.annotation.SuppressLint;
import edu.metu.sucre.model.api.Channel;
import edu.metu.sucre.model.api.Message;
import edu.metu.sucre.views.activities.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

/**
 * Created by ilkay on 02/08/2017.
 */

public class HealthChannelMessagePresenter<V extends HealthChannelMessageMvpView> extends BasePresenter<V>
		implements HealthChannelMessageMvpPresenter<V> {
	public HealthChannelMessagePresenter(edu.metu.sucre.controller.IDataManager IDataManager) {
		super(IDataManager);
	}

	@SuppressLint("CheckResult")
	@Override
	public void sendMessage(Message message) {
		getIDataManager().sendMessage(message)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(aVoid -> System.out.println(), throwable -> System.err.println(throwable.getMessage()));
	}

	@SuppressLint("CheckResult")
	@Override
	public void addUserToChannel(String dialogId, String email) {
		getIDataManager().addUserToChannel(dialogId, email)
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe(channel -> {
			System.out.println();
		}, throwable -> System.err.println(throwable.getMessage()));
	}

	@SuppressLint("CheckResult")
	@Override
	public void getUsersOfChannels(String channelId) {
		getIDataManager().getChannel(channelId)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::getUsers, throwable -> System.err.println(throwable.getMessage()));
	}

	@SuppressLint("CheckResult")
	@Override
	public void getChannel(String channelId) {
		getIDataManager().getChannel(channelId)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(channel -> getMvpView().addChannel(channel),
						throwable -> System.err.println(throwable.getMessage()));
	}

	@SuppressLint("CheckResult")
	@Override
	public void getMessages(String channelId) {
		getIDataManager().getMessages(channelId)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(messages -> {
					for (Message message : messages) {

					}
				}, throwable -> System.err.println(throwable.getMessage()));
	}

	@SuppressLint("CheckResult")
	private void getUsers(Channel channel) {
		List<String> userIds = channel.guestUserIds;
		for (String userId : userIds) {
		    getIDataManager().getUser(userId)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(user -> getMvpView().addUser(user),
							throwable -> System.err.println(throwable.getMessage()));
		}
	}
}
