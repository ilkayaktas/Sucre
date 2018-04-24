package edu.metu.sucre.model.api;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ilkayaktas on 22.04.2018 at 13:14.
 */

public class HealthData {
    public String id; // message id
    public Date date;
    public String userId;
    public String dataText;
    public String dataTextDetail;
    public HealthDataType healthDataType;

    public HealthData(String dataText, String dataTextDetail, HealthDataType healthDataType) {
        this.dataText = dataText;
        this.dataTextDetail = dataTextDetail;
        this.healthDataType = healthDataType;
        date = Calendar.getInstance().getTime();
    }

    public enum HealthDataType{
        ALL(0),
        TREATMENT(1),
        NUTRITION(2),
        ACTIVITY(3);

        private final int value;

        HealthDataType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
