package edu.metu.sucre.views.activities.healthdatalist;


import edu.metu.sucre.views.activities.base.MvpPresenter;

/**
 * Created by ilkay on 02/08/2017.
 */

public interface HealthDataListMvpPresenter<V extends HealthDataListMvpView> extends MvpPresenter<V> {
    void getHealthData(String userId);

    void getUser(String userId);
}
