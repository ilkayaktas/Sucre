package edu.metu.sucre.model.api;

import edu.metu.sucre.model.app.SugarMeasurementType;

import java.util.Date;

/**
 * Created by iaktas on 24.04.2017.
 */

public class BloodSugarData {
    public String id; // message id
    public Date date;
    public int value;
    public String userId;
    public SugarMeasurementType sugarMeasurementType; // 1 means pre, 2 means post

    public BloodSugarData(String id, Date date, int value, SugarMeasurementType sugarMeasurementType, String userId) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.sugarMeasurementType = sugarMeasurementType;
        this.userId = userId;
    }

}
