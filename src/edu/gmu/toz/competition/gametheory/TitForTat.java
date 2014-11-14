package edu.gmu.toz.competition.gametheory;

import java.util.UUID;

/**
 * Simple player that plays the same strategy every game.
 * 
 * @author randy
 *
 */
public class TitForTat extends AbstractGamePlayer {

	@Override
	public GameStrategy getGameStrategy(UUID opponentId,
			GamePayoffs gamePayoffs, GameHistory gameHistory) {

		GameStrategy lastByOpponent = gameHistory.getLastStrategy(opponentId, gamePayoffs, getUniqueId());
		if(lastByOpponent == null)
			return GameStrategy.COOPERATE;
		return lastByOpponent;
	}
}
