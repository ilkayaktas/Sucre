package edu.metu.sucre.model.app;

/**
 * Created by iaktas on 10.05.2017.
 */

public enum SugarMeasurementType {
    PRE(1),
    POST(2);

    private int value;
    SugarMeasurementType(int value) {
        this.value = value;
    }

    public int getValue(){return value;}

}
