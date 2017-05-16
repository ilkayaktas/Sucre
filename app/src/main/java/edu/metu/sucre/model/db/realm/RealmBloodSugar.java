package edu.metu.sucre.model.db.realm;

import java.util.Date;
import java.util.UUID;

import edu.metu.sucre.model.app.BloodSugar;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by iaktas on 10.05.2017.
 */

public class RealmBloodSugar extends RealmObject {
    @PrimaryKey
    public String uuid;
    public Date date;
    public int value;
    public int sugarMeasurementType; // 1 means pre, 2 means post

    public RealmBloodSugar(){

    }

    public RealmBloodSugar(BloodSugar bloodSugar) {
        this.uuid = UUID.randomUUID().toString();
        this.date = bloodSugar.date;
        this.value = bloodSugar.value;
        this.sugarMeasurementType = bloodSugar.sugarMeasurementType.getValue();
    }
}
