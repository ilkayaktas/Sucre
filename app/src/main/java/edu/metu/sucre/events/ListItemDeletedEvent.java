package edu.metu.sucre.events;

/**
 * Created by ilkay on 10/06/2017.
 */

public class ListItemDeletedEvent {
	public final String deletedUuid;
	
	public ListItemDeletedEvent(String deletedUuid) {
		this.deletedUuid = deletedUuid;
	}
}
