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

    public String toString(){
        if(value == 1){
            return "AÇLIK";
        } else if(value == 2){
            return "TOKLUK";
        } else{
            return "";
        }
    }

}
