package edu.metu.sucre.controller.db;


import java.util.List;

import edu.metu.sucre.model.app.BloodSugar;

/**
 * Created by iaktas on 24/04/17.
 */

public interface IDbHelper {
    
    List<BloodSugar> getBloodSugar();
    void saveBloodSugar(BloodSugar bloodSugar);
    void deleteBloodSugar(String uuid);
}
