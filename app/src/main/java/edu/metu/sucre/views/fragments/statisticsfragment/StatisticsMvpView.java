package edu.metu.sucre.views.fragments.statisticsfragment;


import java.util.List;

import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.views.activities.base.MvpView;

/**
 * Created by iaktas on 14.03.2017.
 */

public interface StatisticsMvpView extends MvpView {
	void updateData(List<BloodSugar> bloodSugarList);
}
