package edu.metu.sucre.views.activities.healthdatalist;


import android.annotation.SuppressLint;
import edu.metu.sucre.model.api.HealthData;
import edu.metu.sucre.views.activities.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ilkay on 02/08/2017.
 */

public class HealthDataListPresenter<V extends HealthDataListMvpView> extends BasePresenter<V>
		implements HealthDataListMvpPresenter<V> {
	public HealthDataListPresenter(edu.metu.sucre.controller.IDataManager IDataManager) {
		super(IDataManager);
	}

	@SuppressLint("CheckResult")
	@Override
	public void getHealthData(String userId) {
		getMvpView().setLoading(true);

		getIDataManager().getHealthData(userId, HealthData.HealthDataType.ALL.name())
				.subscribeOn(Schedulers.io())
				.observeOn( AndroidSchedulers.mainThread())
				.subscribe(healthDataList -> getMvpView().showHealthData(healthDataList)
				, throwable -> {
						System.err.println(throwable);
						if(getMvpView()!= null){
							getMvpView().setLoading(false);
						}
					});
	}
}
