package edu.metu.sucre.views.activities.home;


import android.annotation.SuppressLint;
import com.facebook.login.LoginManager;
import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.model.api.FBUser;
import edu.metu.sucre.model.api.User;
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
	public void saveBloodSugar(BloodSugar bloodSugar) {
		getIDataManager().saveBloodSugar(bloodSugar);

		getMvpView().updateUIAfterRecord(bloodSugar);
	}

	@Override
	public void logout() {
		LoginManager.getInstance().logOut();
	}

	@SuppressLint("CheckResult")
	@Override
	public void getFacebookProfile() {
		getIDataManager().getFacebookProfile()
				.subscribeOn(Schedulers.io())
				.observeOn( AndroidSchedulers.mainThread())
				.subscribe(this::saveUser,
						throwable -> System.err.println(throwable.getMessage()));
	}

	@Override
	public boolean isFacebookTokenAvailable() {
		return getIDataManager().getUserId() != null;
	}

	@SuppressLint("CheckResult")
	private void saveUser(FBUser fbUser){
		User user = new User();
		user.name = fbUser.getName();
		user.userId = fbUser.getId();
		user.email = fbUser.getEmail();
		user.picture = fbUser.getPicture().toString();
		user.fcmToken = getIDataManager().getFCMToken();

		getIDataManager().saveUser(user)
				.subscribeOn(Schedulers.io())
				.observeOn( AndroidSchedulers.mainThread())
				.subscribe(user1 -> {
					System.out.println();
				}, this::onError);
	}

	private void onError(Throwable throwable) {
		System.err.println(throwable.getMessage());
	}
}
