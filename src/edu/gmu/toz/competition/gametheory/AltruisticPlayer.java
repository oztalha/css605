package edu.gmu.toz.competition.gametheory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Simple player that plays the same strategy every game.
 * 
 * @author randy
 *
 */
public class AltruisticPlayer extends AbstractGamePlayer {

	static List<UUID> others;
	static List<UUID> siblings;
	
	/**
	 * Zero means I am the worst player when the ranks ordered
	 * One means that I am better than only one player
	 * @param gameHistory
	 * @return my rank
	 */
	public int getMyRank(GameHistory gameHistory){
		int myrank = 0;
		Set <UUID> ids = gameHistory.getGamePlayerIds();
		for (UUID uuid : ids) {
			if(gameHistory.getTotalPayoffs(uuid)< gameHistory.getTotalPayoffs(getUniqueId()))
				myrank++;
		}
		return myrank;
	}
	
	/**
	 * Zero means sibling is the worst player when the ranks ordered
	 * One means that my sibling is better than only one player
	 * @param gameHistory
	 * @return sibling's rank
	 */
	public int getSiblingsRank(GameHistory gameHistory){
		int sibRank = 0;
		UUID sibID;
		if(siblings.size()==2){
			sibID = getUniqueId()==siblings.get(0)?siblings.get(1):siblings.get(0);
		}else
			return 0;
		
		Set <UUID> ids = gameHistory.getGamePlayerIds();
		for (UUID uuid : ids) {
			if(gameHistory.getTotalPayoffs(uuid)< gameHistory.getTotalPayoffs(sibID))
				sibRank++;
		}
		return sibRank;
	}
	
	public int getRank(GameHistory gameHistory, UUID id){
		int rank = 0;
		Set <UUID> ids = gameHistory.getGamePlayerIds();
		for (UUID uuid : ids) {
			if(gameHistory.getTotalPayoffs(uuid)< gameHistory.getTotalPayoffs(id))
				rank++;
		}
		return rank;
	}
	
	public AltruisticPlayer() {
		super();
		setDisplayName("Altruistic tit for tat");
		if(siblings == null)
			siblings = new ArrayList<UUID>(2);
		siblings.add(getUniqueId());
	}
	@Override
	public GameStrategy getGameStrategy(UUID opponentId,
			GamePayoffs gamePayoffs, GameHistory gameHistory) {
		
		switch (gamePayoffs) {
		case PRISONERS_DILEMMA:
			return getPDStrategy(opponentId, gamePayoffs, gameHistory);
		case HAWK_DOVE:
			return getHDStrategy(opponentId, gamePayoffs, gameHistory);
		case STAG_HUNT:
			return getSHStrategy(opponentId, gamePayoffs, gameHistory);
		case COORDINATION:
			return getCOStrategy(opponentId, gamePayoffs, gameHistory);
		case ANTI_COORDINATION:
			return getACStrategy(opponentId, gamePayoffs, gameHistory);
		default:
			return GameStrategy.STRATEGY1;
		}
		
	}

	private GameStrategy getACStrategy(UUID opponentId,
			GamePayoffs gamePayoffs, GameHistory gameHistory) {
		
		if(gameHistory.getGamePlayerCount()==2)
			return GameStrategy.STRATEGY2;
		
		//general strategy is opposite of the last
		GameStrategy lastByOpponent = gameHistory.getLastStrategy(opponentId, gamePayoffs, getUniqueId());
		List<GameStrategy> allByOpponent = gameHistory.getAllStrategies(opponentId, gamePayoffs, getUniqueId());
		GameStrategy lastByMe = gameHistory.getLastStrategy(getUniqueId(), gamePayoffs, opponentId);

		//specific strategy when playing with yourself
		if(siblings.contains(opponentId)){
			GameStrategy s;
			if(gameHistory.getTotalPayoffs(opponentId)<gameHistory.getTotalPayoffs(getUniqueId()))
				s =  GameStrategy.STRATEGY2;
			else if(gameHistory.getTotalPayoffs(opponentId)>gameHistory.getTotalPayoffs(getUniqueId()))
				s = GameStrategy.STRATEGY1; // altruistic move
			else if(opponentId.compareTo(getUniqueId())>0) //if equal then distort the equivalence
				s = GameStrategy.STRATEGY1;
			else
				s = GameStrategy.STRATEGY2;
			if(getMyRank(gameHistory)>1 && getRank(gameHistory, opponentId)>1)
				return s; // rich gets richer
			else // help the brother
				return s == GameStrategy.STRATEGY1 ? GameStrategy.STRATEGY2 : GameStrategy.STRATEGY1;
		}
		if(lastByOpponent == null)
			return GameStrategy.STRATEGY2;
		if(!allByOpponent.contains(GameStrategy.STRATEGY2))
			return GameStrategy.STRATEGY2;
		if(!allByOpponent.contains(GameStrategy.STRATEGY1))
			return GameStrategy.STRATEGY1;
		
		if(lastByMe == lastByOpponent)
			return GameStrategy.STRATEGY1;
		
		if(lastByOpponent == GameStrategy.STRATEGY1)
			return GameStrategy.STRATEGY2;
		else
			return GameStrategy.STRATEGY1;
	}

	private GameStrategy getCOStrategy(UUID opponentId,
			GamePayoffs gamePayoffs, GameHistory gameHistory) {
		if(siblings.contains(opponentId))
			return GameStrategy.STRATEGY1;
		if(gameHistory.getGamePlayerCount()==2)
			return GameStrategy.STRATEGY2;
		//general strategy is tit for tat
		List<GameStrategy> allByOpponent = gameHistory.getAllStrategies(opponentId, gamePayoffs, getUniqueId());
		GameStrategy lastByOpponent = gameHistory.getLastStrategy(opponentId, gamePayoffs, getUniqueId());
		GameStrategy lastByMe = gameHistory.getLastStrategy(getUniqueId(), gamePayoffs, opponentId);
		
		if(lastByOpponent == null)
			return GameStrategy.STRATEGY2;
		if(!allByOpponent.contains(GameStrategy.STRATEGY2))
			return GameStrategy.STRATEGY1;
		if(!allByOpponent.contains(GameStrategy.STRATEGY1))
			return GameStrategy.STRATEGY2;
		if(lastByMe == lastByOpponent)
			return lastByMe;
		return lastByOpponent;
	}

	private GameStrategy getSHStrategy(UUID opponentId,
			GamePayoffs gamePayoffs, GameHistory gameHistory) {
		if(siblings.contains(opponentId))
			return GameStrategy.STRATEGY1;
		if(gameHistory.getGamePlayerCount()==2)
			return GameStrategy.STRATEGY2;
		//general strategy is tit for tat
		GameStrategy lastByOpponent = gameHistory.getLastStrategy(opponentId, gamePayoffs, getUniqueId());
		if(lastByOpponent == null)
			return GameStrategy.STRATEGY1;
		return lastByOpponent;	
	}

	private GameStrategy getHDStrategy(UUID opponentId,
			GamePayoffs gamePayoffs, GameHistory gameHistory) {
		if(gameHistory.getGamePlayerCount()==2)
			return GameStrategy.STRATEGY2;
		// if opponent never plays Strategy2 then play Strategy2
		// otherwise, play Strategy1
		List<GameStrategy> allByOpponent = gameHistory.getAllStrategies(opponentId, gamePayoffs);
		if(allByOpponent == null)
			return GameStrategy.STRATEGY1;
		if (!allByOpponent.contains(GameStrategy.STRATEGY2))
			return GameStrategy.STRATEGY2;
		return GameStrategy.STRATEGY1;
	}

	private GameStrategy getPDStrategy(UUID opponentId, GamePayoffs gamePayoffs,
			GameHistory gameHistory) {
		if(gameHistory.getGamePlayerCount()==2)
			return GameStrategy.STRATEGY2;
		//specific strategy when playing with yourself
		if(siblings.contains(opponentId)){
			//help the better one if you are likely to live the next round!!!
			if(getMyRank(gameHistory)>1){
				if(gameHistory.getTotalPayoffs(opponentId)<gameHistory.getTotalPayoffs(getUniqueId()))
					return GameStrategy.STRATEGY2;
				else if(gameHistory.getTotalPayoffs(opponentId)>gameHistory.getTotalPayoffs(getUniqueId()))
					return GameStrategy.STRATEGY1; // altruistic move
				else if(opponentId.compareTo(getUniqueId())>0) //if equal then distort the equivalence
					return GameStrategy.STRATEGY1;
				else
					return GameStrategy.STRATEGY2;
			}else //help yourself as well
				return GameStrategy.STRATEGY1;
		}
		//strategic altruistic move 2 !
		if(getMyRank(gameHistory)<getSiblingsRank(gameHistory) && getSiblingsRank(gameHistory)<getRank(gameHistory, opponentId))
			return GameStrategy.STRATEGY2;
		//general strategy is tit for tat
		GameStrategy lastByOpponent = gameHistory.getLastStrategy(opponentId, gamePayoffs, getUniqueId());
		if(lastByOpponent == null)
			return GameStrategy.STRATEGY1;
		return lastByOpponent;	
	}
}