package edu.metu.sucre.views.fragments.listfragment;


import android.annotation.SuppressLint;
import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.model.api.BloodSugarData;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.app.SugarMeasurementType;
import edu.metu.sucre.views.activities.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iaktas on 14.03.2017.
 */

public class ListPresenter<V extends ListMvpView> extends BasePresenter<V> implements ListMvpPresenter<V>{
    public ListPresenter(IDataManager IDataManager) {
        super(IDataManager);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getAllBloodSugarMeasurements(String patientId) {
//        List<BloodSugar> bloodSugarList =  getIDataManager().getBloodSugar();
        List<BloodSugar> bloodSugarList =  new ArrayList<>();
        getIDataManager().getBloodSugarFromServer(patientId, SugarMeasurementType.ALL.name())
                .subscribeOn(Schedulers.io())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(bloodSugarData -> {
                    for (BloodSugarData bloodSugarData1 : bloodSugarData) {
                        bloodSugarList.add(new BloodSugar(bloodSugarData1.id,
                                                            bloodSugarData1.date,
                                                            bloodSugarData1.value,
                                                            bloodSugarData1.sugarMeasurementType));
                    }
                }, System.err::println, () -> getMvpView().updateBloodSugarList(bloodSugarList));
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void deleteBloodSugarValue(String uuid) {
//        getIDataManager().deleteBloodSugar(uuid);
        getIDataManager().deleteBloodSugarFromServer(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(bloodSugarData -> {
                    System.out.println("BloodSugarData is deleted: " + bloodSugarData.toString());
                }, System.err::println);
    }
}
