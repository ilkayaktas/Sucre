package edu.metu.sucre.views.fragments.listfragment;


import java.util.List;

import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.views.activities.base.MvpView;

/**
 * Created by iaktas on 14.03.2017.
 */

public interface ListMvpView extends MvpView {
    void updateBloodSugarList(List<BloodSugar> bloodSugarList);
}
