package edu.metu.sucre.views.activities.home;


import java.util.List;

import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.app.ListItem;
import edu.metu.sucre.views.activities.base.MvpView;

/**
 * Created by ilkay on 12/03/2017.
 */

public interface MainMvpView extends MvpView {
    void updateListView(List<ListItem> sugarValues);

    void updateUIAfterRecord(BloodSugar bloodSugar);
}
