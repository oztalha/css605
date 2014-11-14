package edu.gmu.toz.competition.gametheory;

import java.util.UUID;

/**
 * Your GamePlayer should extend the AbstractGamePlayer class. Therefore, the
 * only method you are required to implement is the getGameStrategy method.
 * 
 * @author randy
 *
 */
public interface GamePlayer {

	public UUID getUniqueId();

	public String getDisplayName();

	public void setDisplayName(String displayName);

	public GameStrategy getGameStrategy(UUID opponentId,
			GamePayoffs gamePayoffs, GameHistory gameHistory);
}
