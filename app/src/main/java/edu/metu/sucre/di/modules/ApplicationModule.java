package edu.metu.sucre.di.modules;

import android.app.Application;
import android.content.Context;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import dagger.Module;
import dagger.Provides;
import edu.metu.sucre.BuildConfig;
import edu.metu.sucre.controller.DataManager;
import edu.metu.sucre.controller.IDataManager;
import edu.metu.sucre.controller.api.ApiHelper;
import edu.metu.sucre.controller.api.IApiHelper;
import edu.metu.sucre.controller.api.backend.BackendService;
import edu.metu.sucre.controller.db.DbHelper;
import edu.metu.sucre.controller.db.IDbHelper;
import edu.metu.sucre.controller.db.crud.DatabaseManager;
import edu.metu.sucre.controller.db.crud.DatabaseMigration;
import edu.metu.sucre.controller.db.crud.RealmManager;
import edu.metu.sucre.controller.pref.IPreferenceHelper;
import edu.metu.sucre.controller.pref.PreferenceHelper;
import edu.metu.sucre.di.annotations.ApplicationContext;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

/**
 * Created by ilkay on 09/03/2017.
 */

@Module
public class ApplicationModule {
	
	private final Application app;
	private RealmConfiguration realmConfiguration = null;
	private DatabaseManager databaseManager;
	
	public ApplicationModule(Application app) {
		this.app = app;
	}
	
	@Provides
	@ApplicationContext
	Context provideContext() {
		return app;
	}
	
	@Provides
	Application provideApplication(){
		return app;
	}
	
	@Provides
	@Singleton
	IDataManager provideDataManager(@ApplicationContext Context context, IDbHelper mIDbHelper, IPreferenceHelper mIPreferenceHelper, IApiHelper mIApiHelper) {
		return new DataManager( context, mIDbHelper, mIPreferenceHelper, mIApiHelper);
	}
	
	@Provides
	@Singleton
	IDbHelper provideDbHelper(DatabaseManager databaseManager) {
		return new DbHelper(databaseManager);
	}
	
	@Provides
	@Singleton
	DatabaseManager provideDatabaseManager(Realm realm){
		databaseManager =  new RealmManager(realm);
		return databaseManager;
	}
	
	@Provides
	@Singleton
	Realm provideRealm(){
		
		if(realmConfiguration == null) {
			// Create a RealmConfiguration that saves the Realm file in the app's "files" directory.
			realmConfiguration = new RealmConfiguration.Builder()
					.name("sucre.db")
					.migration(new DatabaseMigration())
					.encryptionKey(new String("YhvPohxPIDXI8wneZTgYwFElAuSeWOhea8WILKRvuHeiOQYaz1RLZ4m0ZEaAP7Gc").getBytes())
					.schemaVersion(1)
					.build();
		}
		
		// Get a Realm instance for this thread
		Realm realm = Realm.getInstance(realmConfiguration);
		
		return realm;
	}
	
	@Provides
	@Singleton
	IPreferenceHelper providePreferencesHelper(@ApplicationContext Context context) {
		return new PreferenceHelper(context);
	}
	
	@Provides
	@Singleton
	IApiHelper provideApiHelper() {
		return new ApiHelper();
	}

	@Provides
	@Singleton
	BackendService provideBackendService(){
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		// set your desired log level
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient okClient = new OkHttpClient.Builder()
				.addInterceptor(logging)
				.build();

		Retrofit retrofitApi = new Retrofit.Builder()
				.baseUrl(BuildConfig.BACKEND_ENDPOINT)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.client(okClient)
				.build();

		return retrofitApi.create(BackendService.class);
	}
}
