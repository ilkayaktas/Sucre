package edu.metu.sucre.views.activities.channels;


import edu.metu.sucre.views.activities.base.MvpPresenter;

/**
 * Created by ilkay on 02/08/2017.
 */

public interface HealthChannelsMvpPresenter<V extends HealthChannelsMvpView> extends MvpPresenter<V> {
    void addChannel(String channelName);

    void getUserChannels();
}
