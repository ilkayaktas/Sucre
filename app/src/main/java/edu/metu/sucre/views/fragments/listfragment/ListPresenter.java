package edu.metu.sucre.views.fragments.listfragment;


import java.util.List;

import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.views.activities.base.BasePresenter;

/**
 * Created by iaktas on 14.03.2017.
 */

public class ListPresenter<V extends ListMvpView> extends BasePresenter<V> implements ListMvpPresenter<V>{
    public ListPresenter(IDataManager IDataManager) {
        super(IDataManager);
    }

    @Override
    public void getAllBloodSugarMeasurements() {
        List<BloodSugar> bloodSugarList =  getIDataManager().getBloodSugar();
        getMvpView().updateBloodSugarList(bloodSugarList);
    }
}
