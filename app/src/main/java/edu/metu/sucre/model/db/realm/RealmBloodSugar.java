package edu.metu.sucre.model.db.realm;

import java.util.Date;

import edu.metu.sucre.model.app.BloodSugar;
import io.realm.RealmObject;

/**
 * Created by iaktas on 10.05.2017.
 */

public class RealmBloodSugar extends RealmObject {
    public Date date;
    public int value;
    public int sugarMeasurementType; // 1 means pre, 2 means post

    public RealmBloodSugar(){

    }

    public RealmBloodSugar(BloodSugar bloodSugar) {
        this.date = bloodSugar.date;
        this.value = bloodSugar.value;
        this.sugarMeasurementType = bloodSugar.sugarMeasurementType.getValue();
    }
}
