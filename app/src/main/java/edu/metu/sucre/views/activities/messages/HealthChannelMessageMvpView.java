package edu.metu.sucre.views.activities.messages;


import edu.metu.sucre.model.api.Channel;
import edu.metu.sucre.model.api.User;
import edu.metu.sucre.model.app.DialogMessage;
import edu.metu.sucre.views.activities.base.MvpView;

/**
 * Created by ilkay on 02/08/2017.
 */

public interface HealthChannelMessageMvpView extends MvpView {
    void addUser(User user);

    void addChannel(Channel channel);

    void onNewMessage(DialogMessage message);
}
