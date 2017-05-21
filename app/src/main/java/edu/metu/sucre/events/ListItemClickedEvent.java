package edu.metu.sucre.events;

/**
 * Created by iaktas on 24.04.2017.
 */

public class ListItemClickedEvent {
	public final int position;
	
	public ListItemClickedEvent(int position) {
		this.position = position;
	}
}
