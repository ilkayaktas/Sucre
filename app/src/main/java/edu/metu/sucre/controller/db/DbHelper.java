package edu.metu.sucre.controller.db;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.metu.sucre.controller.db.crud.DatabaseManager;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.app.SugarMeasurementType;
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
    public List<BloodSugar> getBloodSugar() {
        List<RealmBloodSugar> realmBloodSugarList = databaseManager.getAll(RealmBloodSugar.class, "date", true);

        List<BloodSugar> bloodSugarList = new ArrayList<>();

        for (RealmBloodSugar realmBloodSugar : realmBloodSugarList) {
            bloodSugarList.add(new BloodSugar(realmBloodSugar.date,
                                                realmBloodSugar.value,
                    realmBloodSugar.sugarMeasurementType == 1 ? SugarMeasurementType.PRE : SugarMeasurementType.POST));
        }
        return bloodSugarList;
    }

    @Override
    public void saveBloodSugar(BloodSugar bloodSugar) {
        databaseManager.saveOrUpdate(new RealmBloodSugar(bloodSugar));
    }

}
