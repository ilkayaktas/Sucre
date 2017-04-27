package edu.metu.sucre.views.fragments.listfragment;


import edu.metu.sucre.controller.DataManager;
import edu.metu.sucre.views.activities.base.BasePresenter;

/**
 * Created by iaktas on 14.03.2017.
 */

public class ListPresenter<V extends ListMvpView> extends BasePresenter<V> implements ListMvpPresenter<V>{
    public ListPresenter(DataManager dataManager) {
        super(dataManager);
    }

}
