package edu.metu.sucre.views.activities.sugarlevel;

import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.views.activities.base.BasePresenter;

/**
 * Created by ilkay on 27/04/2017.
 */

public class SugarLevelPresenter <V extends SugarLevelMvpView> extends BasePresenter<V>
		implements SugarLevelMvpPresenter<V> {
	
	public SugarLevelPresenter(IDataManager IDataManager) {
		super(IDataManager);
	}
}
