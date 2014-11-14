package edu.gmu.toz.competition.gametheory;

import java.util.UUID;

/**
 * Simple player that plays the same strategy every game.
 * 
 * @author randy
 *
 */
public class Adaptive extends AbstractGamePlayer {

	private double s1payoffs = 0;
	private double s2payoffs = 0;
	private int s1count = 0;
	private int s2count = 0;
	
	@Override
	public GameStrategy getGameStrategy(UUID opponentId,
			GamePayoffs gamePayoffs, GameHistory gameHistory) {
		if(gameHistory.getNumberOfGamesPlayed()<5)
			return GameStrategy.STRATEGY1;
		else if(gameHistory.getNumberOfGamesPlayed()<10)
			return GameStrategy.STRATEGY2;
		return (s1payoffs/s1count > s2payoffs/s2count)? GameStrategy.STRATEGY1 : GameStrategy.STRATEGY2;

	}
}
