package edu.metu.sucre.views.fragments.listfragment;


import edu.metu.sucre.views.activities.base.MvpPresenter;

/**
 * Created by iaktas on 14.03.2017.
 */

public interface ListMvpPresenter <V extends ListMvpView> extends MvpPresenter<V> {
    void getAllBloodSugarMeasurements();
    
    void deleteBloodSugarValue(String uuid);
}
