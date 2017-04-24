package edu.metu.sucre.views.activities.home;


import edu.metu.sucre.controller.DataManager;
import edu.metu.sucre.views.activities.base.BasePresenter;

/**
 * Created by ilkay on 12/03/2017.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
		implements MainMvpPresenter<V>{
	
	public MainPresenter(DataManager dataManager) {
		super(dataManager);
	}
	
	@Override
	public void getKategoriler() {
	}
	
	@Override
	public void getKavramlar(int kategoriId) {
	}


	@Override
	public void getFavoriKavramlar() {
	}
}
