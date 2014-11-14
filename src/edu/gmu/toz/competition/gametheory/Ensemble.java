package edu.gmu.toz.competition.gametheory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Simple player that plays the same strategy every game.
 * 
 * @author randy
 *
 */
public class Ensemble extends AbstractGamePlayer {

	private double s1payoffs = 0;
	private double s2payoffs = 0;
	private int s1count = 0;
	private int s2count = 0;
	private GameStrategy lastPlayed;
	
	@Override
	public GameStrategy getGameStrategy(UUID opponentId,
			GamePayoffs gamePayoffs, GameHistory gameHistory) {
		//reset the variables
		if(gameHistory.getNumberOfGamesPlayed()==0){
			System.out.println(gameHistory.getGamePlayerCount());
			s1payoffs = 0;
			s2payoffs = 0;
			s1count = 0;
			s2count = 0;
		}
			
		if(gameHistory.getGamePlayerCount()>6){
			if(gameHistory.getNumberOfGamesPlayed()<5){
				s1count++;
				s1payoffs = gameHistory.getTotalPayoffs(getUniqueId());
				return GameStrategy.STRATEGY1;
			}
				
			else if(gameHistory.getNumberOfGamesPlayed()<10){
				s2count++;
				s2payoffs = gameHistory.getTotalPayoffs(getUniqueId()) - s1payoffs;
				lastPlayed = GameStrategy.STRATEGY2;
				return GameStrategy.STRATEGY2;
			}
			
			if(lastPlayed == GameStrategy.STRATEGY2){
				s2payoffs = gameHistory.getTotalPayoffs(getUniqueId()) - s1payoffs;
			}else{
				s1payoffs = gameHistory.getTotalPayoffs(getUniqueId()) - s2payoffs;
			}
			
			if(s1payoffs/s1count > s2payoffs/s2count){
				s1count++;
				lastPlayed = GameStrategy.STRATEGY1;
				return GameStrategy.STRATEGY1;			
			}else{
				lastPlayed = GameStrategy.STRATEGY2;
				return GameStrategy.STRATEGY2;
			}
		}else{			
			GameStrategy lastByOpponent = gameHistory.getLastStrategy(opponentId, gamePayoffs, getUniqueId());
			if(lastByOpponent == null)
				return GameStrategy.STRATEGY1;
			return GameStrategy.STRATEGY1==lastByOpponent?GameStrategy.STRATEGY1:GameStrategy.STRATEGY2;
		}
	}
}
