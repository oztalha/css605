package edu.gmu.toz.competition.gametheory;

import java.util.UUID;

/**
 * Simple player that plays the same strategy every game.
 * 
 * @author randy
 *
 */
public class TfTR extends AbstractGamePlayer {

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
			
			if(Math.random()<0.9)
				return lastByOpponent;
			else
				return GameStrategy.DEFECT;
			
		case HAWK_DOVE:
			if(lastByOpponent == null)
				return GameStrategy.COOPERATE;
			
			if(Math.random()<0.9)
				return lastByOpponent;
			else
				return GameStrategy.COOPERATE==lastByOpponent?GameStrategy.DEFECT:GameStrategy.COOPERATE;			
		case STAG_HUNT:
			if(lastByOpponent == null)
				return GameStrategy.COOPERATE;
			
			if(Math.random()<0.9)
				return lastByOpponent;
			else
				return GameStrategy.COOPERATE==lastByOpponent?GameStrategy.DEFECT:GameStrategy.COOPERATE;
		default:
			break;
		}
		return GameStrategy.STRATEGY1;
	}
}
