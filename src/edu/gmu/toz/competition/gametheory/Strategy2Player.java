package edu.gmu.toz.competition.gametheory;

import java.util.UUID;

/**
 * Simple player that plays the same strategy every game.
 * 
 * @author randy
 *
 */
public class Strategy2Player extends AbstractGamePlayer {

	@Override
	public GameStrategy getGameStrategy(UUID opponentId,
			GamePayoffs gamePayoffs, GameHistory gameHistory) {
		
		/*
		 * There could be a lot of logic in this method, but just to give you a
		 * simple example implementation, this method returns the same strategy
		 * every game.
		 */
		
		return GameStrategy.STRATEGY2;
	}
}
