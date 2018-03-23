package edu.metu.sucre.views.activities.home;


import com.facebook.login.LoginManager;
import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.views.activities.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ilkay on 12/03/2017.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
		implements MainMvpPresenter<V>{
	
	public MainPresenter(IDataManager IDataManager) {
		super(IDataManager);
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

	@Override
	public void saveBloodSugar(BloodSugar bloodSugar) {
		getIDataManager().saveBloodSugar(bloodSugar);

		getMvpView().updateUIAfterRecord(bloodSugar);
	}

	@Override
	public void logout() {
		LoginManager.getInstance().logOut();
	}

	@Override
	public void getFacebookProfile() {
		getIDataManager().getFacebookProfile()
				.subscribeOn(Schedulers.io())
				.observeOn( AndroidSchedulers.mainThread())
				.subscribe( user -> System.out.println(user.toString()),
							throwable -> System.out.println(throwable.toString()),
							() -> System.out.println("completed"));
	}
}
