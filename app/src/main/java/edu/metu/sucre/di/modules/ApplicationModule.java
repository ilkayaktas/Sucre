package edu.metu.sucre.di.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.metu.sucre.controller.DataManager;
import edu.metu.sucre.controller.DataManagerImpl;
import edu.metu.sucre.controller.api.ApiHelper;
import edu.metu.sucre.controller.api.ApiHelperImpl;
import edu.metu.sucre.controller.db.DbHelper;
import edu.metu.sucre.controller.db.DbHelperImpl;
import edu.metu.sucre.controller.db.crud.DatabaseManager;
import edu.metu.sucre.controller.db.crud.DatabaseMigration;
import edu.metu.sucre.controller.db.crud.RealmManager;
import edu.metu.sucre.controller.pref.PreferenceHelper;
import edu.metu.sucre.controller.pref.PreferenceHelperImpl;
import edu.metu.sucre.di.annotations.ApplicationContext;
import io.realm.Realm;
import io.realm.RealmConfiguration;

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
	DataManager provideDataManager(@ApplicationContext Context context, DbHelper mDbHelper, PreferenceHelper mPreferenceHelper, ApiHelper mApiHelper) {
		return new DataManagerImpl( context, mDbHelper, mPreferenceHelper, mApiHelper);
	}
	
	@Provides
	@Singleton
	DbHelper provideDbHelper(DatabaseManager databaseManager) {
		return new DbHelperImpl(databaseManager);
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
					.name("mobss.db")
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
	PreferenceHelper providePreferencesHelper(@ApplicationContext Context context) {
		return new PreferenceHelperImpl(context);
	}
	
	@Provides
	@Singleton
	ApiHelper provideApiHelper() {
		return new ApiHelperImpl();
	}
	
}
