package edu.metu.sucre.views.activities.home;


import edu.metu.sucre.model.api.HealthData;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.app.ListItem;
import edu.metu.sucre.views.activities.base.MvpView;

import java.util.List;

/**
 * Created by ilkay on 12/03/2017.
 */

public interface MainMvpView extends MvpView {
    void updateListView(List<ListItem> sugarValues);

    void updateUIAfterRecord(BloodSugar bloodSugar);

    void healthDataSaved(HealthData healthData);
}
