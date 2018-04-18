package edu.metu.sucre.views.activities.messages;


import android.annotation.SuppressLint;
import edu.metu.sucre.views.activities.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ilkay on 02/08/2017.
 */

public class HealthChannelMessagePresenter<V extends HealthChannelMessageMvpView> extends BasePresenter<V>
		implements HealthChannelMessageMvpPresenter<V> {
	public HealthChannelMessagePresenter(edu.metu.sucre.controller.IDataManager IDataManager) {
		super(IDataManager);
	}

	@Override
	public void sendMessage(String to, String message) {

	}

	@SuppressLint("CheckResult")
	@Override
	public void addUserToChannel(String dialogId, String email) {
		getIDataManager().addUserToChannel(dialogId, email)
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe(channel -> {
			System.out.println();
		}, throwable -> {

		});
	}
}
