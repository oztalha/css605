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
public class AltruisticTitForTat extends AbstractGamePlayer {

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
	
	public AltruisticTitForTat() {
		super();
		setDisplayName("Altruistic tit for tat");
		if(siblings == null)
			siblings = new ArrayList<UUID>(2);
		siblings.add(getUniqueId());
	}
	@Override
	public GameStrategy getGameStrategy(UUID opponentId,
			GamePayoffs gamePayoffs, GameHistory gameHistory) {
		
		//specific strategy when playing with yourself
		if(siblings.contains(opponentId)){
			//help the better one if you are likely to live the next round!!!
			if(getMyRank(gameHistory)>1){
				if(gameHistory.getTotalPayoffs(opponentId)<gameHistory.getTotalPayoffs(getUniqueId()))
					return GameStrategy.STRATEGY2;
				else if(gameHistory.getTotalPayoffs(opponentId)>gameHistory.getTotalPayoffs(getUniqueId()))
					return GameStrategy.STRATEGY1;
				else if(opponentId.compareTo(getUniqueId())>0) //if equal then distort the equivalence
					return GameStrategy.STRATEGY1;
				else
					return GameStrategy.STRATEGY2;
			}else //help yourself as well
				return GameStrategy.STRATEGY1;
		}
		//altruistic move !
		if(getMyRank(gameHistory)<getSiblingsRank(gameHistory) && getSiblingsRank(gameHistory)<getRank(gameHistory, opponentId))
			return GameStrategy.STRATEGY2;
		//general strategy is tit for tat
		GameStrategy lastByOpponent = gameHistory.getLastStrategy(opponentId, gamePayoffs, getUniqueId());
		//special case for hawk dove
		if(lastByOpponent == null || gamePayoffs == GamePayoffs.HAWK_DOVE)
			return GameStrategy.STRATEGY1;
		//if not hawk dove and other special conditions then play tit for tat
		return lastByOpponent;
	}
}
