package edu.metu.sucre.model.app;

/**
 * Created by iaktas on 14.02.2016.
 */
public class ListItem {
    public String uuid;
    public int sugarLevel;
    public String date;
    public String prePost;


    public ListItem(String uuid, int sugarLevel, String date, String prePost) {
        this.uuid = uuid;
        this.sugarLevel = sugarLevel;
        this.date = date;
        this.prePost = prePost;
    }
}
