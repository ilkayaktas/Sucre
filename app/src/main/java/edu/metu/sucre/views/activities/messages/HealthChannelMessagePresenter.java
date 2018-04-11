package edu.metu.sucre.views.activities.messages;


import edu.metu.sucre.views.activities.base.BasePresenter;

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
}
