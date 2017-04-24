package edu.metu.sucre.controller.db;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.metu.sucre.controller.db.crud.DatabaseManager;
import edu.metu.sucre.model.app.BloodSugar;

/**
 * Created by ilkay on 12/03/2017.
 */

@Singleton
public class DbHelperImpl implements DbHelper {
	
	private final DatabaseManager databaseManager;
	
	@Inject
	public DbHelperImpl(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	@Override
	public void initializeDatabase() {

	}
	
	@Override
	public List<BloodSugar> getBloodSugar() {
		return null;
	}
	
}
