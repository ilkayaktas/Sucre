package edu.metu.sucre.model.app;

import java.util.Date;

/**
 * Created by iaktas on 24.04.2017.
 */

public class BloodSugar {
    public Date date;
    public int value;
    public SugarMeasurementType sugarMeasurementType; // 1 means pre, 2 means post

    public BloodSugar(Date date, int value, SugarMeasurementType sugarMeasurementType) {
        this.date = date;
        this.value = value;
        this.sugarMeasurementType = sugarMeasurementType;
    }
}
