package edu.metu.sucre.controller.db;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.metu.sucre.controller.db.crud.DatabaseManager;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.db.realm.RealmBloodSugar;

/**
 * Created by ilkay on 12/03/2017.
 */

@Singleton
public class DbHelper implements IDbHelper {
	
	private final DatabaseManager databaseManager;
	
	@Inject
	public DbHelper(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	@Override
	public void initializeDatabase() {

	}
	
	@Override
	public List<BloodSugar> getBloodSugar() {
		return null;
	}

	@Override
	public void saveBloodSugar(BloodSugar bloodSugar) {
		databaseManager.saveOrUpdate(new RealmBloodSugar(bloodSugar));
	}

}
