package edu.metu.sucre.model.app;

import java.util.List;

/**
 * Created by iaktas on 05.06.2017.
 */

public class CardItem {
    public List<BloodSugar> bloodSugarListOfDay;

    public CardItem(List<BloodSugar> bloodSugarListOfDay) {
        this.bloodSugarListOfDay = bloodSugarListOfDay;
    }
}

