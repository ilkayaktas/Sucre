package edu.metu.sucre.views.activities.login;


import edu.metu.sucre.views.activities.base.BasePresenter;

/**
 * Created by ilkay on 02/08/2017.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
		implements LoginMvpPresenter<V> {
	public LoginPresenter(edu.metu.sucre.controller.IDataManager IDataManager) {
		super(IDataManager);
	}
}
