package edu.gmu.toz.competition.gametheory;

import java.util.UUID;

/**
 * This is the class that you should extend. For simple examples, see
 * Strategy1Player and Strategy2Player, which play the same strategy during
 * every game. Your implementation should have some additional logic to help
 * improve its performance.
 * 
 * The only method you are required to implement is the getGameStrategy method.
 * 
 * @author randy
 *
 */
public abstract class AbstractGamePlayer implements GamePlayer {

	public static int instanceCount = 0;

	private final UUID uniqueId = UUID.randomUUID();

	private String displayName;
	
	public AbstractGamePlayer() {
		instanceCount++;
	}

	public final UUID getUniqueId() {
		return uniqueId;
	}

	public final String getDisplayName() {
		return displayName;
	}

	public final void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String toString() {
		return displayName;
	}
}
