package edu.gmu.toz.competition.gametheory;

import java.util.UUID;

/**
 * Simple player that plays the same strategy every game.
 * 
 * @author randy
 *
 */
public class Player1 extends AbstractGamePlayer {

	@Override
	public GameStrategy getGameStrategy(UUID opponentId,
			GamePayoffs gamePayoffs, GameHistory gameHistory) {

		GameStrategy lastByOpponent = gameHistory.getLastStrategy(opponentId, gamePayoffs, getUniqueId());
		switch (gamePayoffs) {
		case PRISONERS_DILEMMA:
			// if opponent crashes I get the max.
			// what this guy played against me last time.
			// last time crashed
			// hard to beat the strategy2 for now?
			
			if(lastByOpponent == null)
				return GameStrategy.COOPERATE;
			return lastByOpponent;
			
		case HAWK_DOVE:
			if(lastByOpponent == null)
				return GameStrategy.COOPERATE;
			return lastByOpponent;
			
		case STAG_HUNT:
			if(lastByOpponent == null)
				return GameStrategy.COOPERATE;
			return lastByOpponent;

		default:
			return GameStrategy.STRATEGY1;
		}

	}
}
