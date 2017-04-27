package edu.metu.sucre.views.fragments.statisticsfragment;


import edu.metu.sucre.controller.DataManager;
import edu.metu.sucre.views.activities.base.BasePresenter;
import edu.metu.sucre.views.fragments.listfragment.ListMvpPresenter;
import edu.metu.sucre.views.fragments.listfragment.ListMvpView;

/**
 * Created by iaktas on 14.03.2017.
 */

public class StatisticsPresenter<V extends StatisticsMvpView> extends BasePresenter<V> implements StatisticsMvpPresenter<V>{
    public StatisticsPresenter(DataManager dataManager) {
        super(dataManager);
    }

}
