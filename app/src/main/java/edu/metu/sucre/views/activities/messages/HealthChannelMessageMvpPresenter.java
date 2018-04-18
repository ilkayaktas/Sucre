package edu.metu.sucre.views.activities.messages;


import edu.metu.sucre.model.api.Message;
import edu.metu.sucre.views.activities.base.MvpPresenter;

/**
 * Created by ilkay on 02/08/2017.
 */

public interface HealthChannelMessageMvpPresenter<V extends HealthChannelMessageMvpView> extends MvpPresenter<V> {
    void sendMessage(Message message);

    void addUserToChannel(String dialogId, String email);

    void getUsersOfChannels(String channelId);

    void getChannel(String channelId);
}
