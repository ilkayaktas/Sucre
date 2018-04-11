package edu.metu.sucre.views.activities.channels;


import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.views.activities.base.BasePresenter;

/**
 * Created by ilkay on 02/08/2017.
 */

public class HealthChannelsPresenter<V extends HealthChannelsMvpView> extends BasePresenter<V>
		implements HealthChannelsMvpPresenter<V> {
	public HealthChannelsPresenter(IDataManager IDataManager) {
		super(IDataManager);
	}
}
