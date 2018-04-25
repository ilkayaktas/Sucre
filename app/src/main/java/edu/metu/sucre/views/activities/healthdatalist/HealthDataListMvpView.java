package edu.metu.sucre.views.activities.healthdatalist;


import edu.metu.sucre.model.api.HealthData;
import edu.metu.sucre.views.activities.base.MvpView;

import java.util.List;

/**
 * Created by ilkay on 02/08/2017.
 */

public interface HealthDataListMvpView extends MvpView {

    void showHealthData(List<HealthData> healthDataList);

    void setLoading(boolean isLoading);
}
