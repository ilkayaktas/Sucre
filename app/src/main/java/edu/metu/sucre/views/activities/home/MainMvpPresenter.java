package edu.metu.sucre.views.activities.home;


import edu.metu.sucre.model.api.HealthData;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.views.activities.base.MvpPresenter;

/**
 * Created by ilkay on 12/03/2017.
 */

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

	void saveBloodSugar(BloodSugar bloodSugar);

	void logout();

	void getFacebookProfile();

	boolean isFacebookTokenAvailable();

	void saveHealthData(HealthData healthData);
}
