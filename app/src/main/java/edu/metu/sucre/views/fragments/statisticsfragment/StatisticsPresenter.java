package edu.metu.sucre.views.fragments.statisticsfragment;


import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.views.activities.base.BasePresenter;

/**
 * Created by iaktas on 14.03.2017.
 */

public class StatisticsPresenter<V extends StatisticsMvpView> extends BasePresenter<V> implements StatisticsMvpPresenter<V>{
    public StatisticsPresenter(IDataManager IDataManager) {
        super(IDataManager);
    }

}
