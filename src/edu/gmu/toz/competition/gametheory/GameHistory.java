package edu.gmu.toz.competition.gametheory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Class for storing the history of the games. You can use what other players
 * did in the past to help inform your strategy for the next game.
 * 
 * @author randy
 *
 */
public class GameHistory {

	private Map<UUID, GamePlayerRecord> idToRecordMap = new HashMap<UUID, GamePlayerRecord>();

	// Since it is final, the following attribute must be set in constructor and
	// cannot be changed for lifetime of instance
	final private int gamesToBePlayed;

	private int gamesPlayed;

	public GameHistory(List<UUID> gamePlayerIds, int gamesToBePlayed) {

		for (UUID playerId : gamePlayerIds) {
			idToRecordMap.put(playerId, new GamePlayerRecord(playerId,
					gamePlayerIds));
		}

		this.gamesToBePlayed = gamesToBePlayed;
		this.gamesPlayed = 0;
	}

	/**
	 * Returns the total number of games that will be played in this round.
	 * 
	 * @return
	 */
	public int getNumberOfGamesToBePlayed() {
		return gamesToBePlayed;
	}

	/**
	 * Returns the number of games played so far in this round.
	 * 
	 * @return
	 */
	public int getNumberOfGamesPlayed() {
		return gamesPlayed;
	}

	/**
	 * Returns the number of games remaining to be played in this round.
	 * 
	 * @return
	 */
	public int getNumberOfGamesRemaining() {
		return gamesToBePlayed - gamesPlayed;
	}

	/**
	 * Returns the last strategy used by the player input parameter (regardless
	 * of game).
	 * 
	 * @param playerId
	 * @return
	 */
	public GameStrategy getLastStrategy(UUID playerId) {

		GamePlayerRecord record = idToRecordMap.get(playerId);

		return record == null ? null : record.getLastStrategy();
	}

	/**
	 * Returns all strategies played by the player input parameter.
	 * 
	 * @param playerId
	 * @return
	 */
	public List<GameStrategy> getAllStrategies(UUID playerId) {

		GamePlayerRecord record = idToRecordMap.get(playerId);

		return record == null ? null : record.getAllStrategies();
	}

	/**
	 * Returns the last strategy used by the player for the specified game.
	 * 
	 * @param playerId
	 * @param game
	 * @return
	 */
	public GameStrategy getLastStrategy(UUID playerId, GamePayoffs game) {

		GamePlayerRecord record = idToRecordMap.get(playerId);

		return record == null ? null : record.getLastStrategy(game);
	}

	/**
	 * Returns all strategies used by the player for the specified game.
	 * 
	 * @param playerId
	 * @param game
	 * @return
	 */
	public List<GameStrategy> getAllStrategies(UUID playerId, GamePayoffs game) {

		GamePlayerRecord record = idToRecordMap.get(playerId);

		return record == null ? null : record.getAllStrategies(game);
	}

	/**
	 * Returns the last strategy played in the specified game by player 1 when
	 * playing player 2.
	 * 
	 * @param playerId1
	 * @param playerId2
	 * @param game
	 * @return
	 */
	public GameStrategy getLastStrategy(UUID playerId1, GamePayoffs game,
			UUID playerId2) {

		GamePlayerRecord record = idToRecordMap.get(playerId1);

		return record == null ? null : record.getLastStrategy(game, playerId2);
	}

	/**
	 * Returns all strategies played in the specified game by player 1 when
	 * playing player 2.
	 * 
	 * @param playerId1
	 * @param game
	 * @param playerId2
	 * @return
	 */
	public List<GameStrategy> getAllStrategies(UUID playerId1,
			GamePayoffs game, UUID playerId2) {

		GamePlayerRecord record = idToRecordMap.get(playerId1);

		return record == null ? null : record.getAllStrategies(game, playerId2);
	}

	/**
	 * Returns the total payoffs so far for the specified player.
	 * 
	 * @param playerId
	 * @return
	 */
	public double getTotalPayoffs(UUID playerId) {

		GamePlayerRecord record = idToRecordMap.get(playerId);

		return record == null ? 0 : record.getTotalPayoff();
	}

	/**
	 * Returns the number of players during this round.
	 * 
	 * @return
	 */
	public int getGamePlayerCount() {
		return idToRecordMap.keySet().size();
	}

	/**
	 * Returns the ids of the players playing this round.
	 * 
	 * @return
	 */
	public Set<UUID> getGamePlayerIds() {
		return Collections.unmodifiableSet(idToRecordMap.keySet());
	}

	/**
	 * Not to be used by students.
	 * 
	 * This is for record keeping to update what strategies have been played.
	 * 
	 * @param game
	 * @param playerId1
	 * @param playerId2
	 * @param strategy1
	 * @param strategy2
	 */
	void addStrategy(GamePayoffs game, UUID playerId1, UUID playerId2,
			GameStrategy strategy1, GameStrategy strategy2) {
		if (playerId1.equals(playerId2)) {
			throw new IllegalArgumentException("player ids cannot be the same");
		}

		GamePlayerRecord record1 = idToRecordMap.get(playerId1);
		record1.addStrategy(game, strategy1, playerId2);
		record1.addPayoff(game.getPayoff(strategy1, strategy2));

		GamePlayerRecord record2 = idToRecordMap.get(playerId2);
		record2.addStrategy(game, strategy2, playerId1);
		record2.addPayoff(game.getPayoff(strategy2, strategy1));
	}

	void setNumberOfGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	/**
	 * Inner class used to store the info for a single player.
	 * 
	 * @author randy
	 *
	 */
	private class GamePlayerRecord {
		private List<GameStrategy> allStrategies = new ArrayList<GameStrategy>();

		private Map<GamePayoffs, List<GameStrategy>> gameToStrategiesMap = new HashMap<GamePayoffs, List<GameStrategy>>();

		private Map<GamePayoffsAndPlayerIdPair, List<GameStrategy>> gameAndPlayerToStrategiesMap = new HashMap<GamePayoffsAndPlayerIdPair, List<GameStrategy>>();

		private double totalPayoffs = 0.0;

		final private GamePayoffsAndPlayerIdPair tempGamePayoffsAndPlayerIdPair = new GamePayoffsAndPlayerIdPair();

		private GamePlayerRecord(UUID playerId, List<UUID> playerIds) {

			for (GamePayoffs gamePayoffs : GamePayoffs.values()) {
				gameToStrategiesMap.put(gamePayoffs,
						new ArrayList<GameStrategy>());

				for (UUID opponentId : playerIds) {
					if (!opponentId.equals(playerId)) {

						GamePayoffsAndPlayerIdPair gamePayoffsOpponentPair = new GamePayoffsAndPlayerIdPair(
								gamePayoffs, opponentId);

						gameAndPlayerToStrategiesMap.put(
								gamePayoffsOpponentPair,
								new ArrayList<GameStrategy>());
					}
				}
			}

		}

		private void addStrategy(GamePayoffs game, GameStrategy strategyPlayed,
				UUID opponentId) {

			// Update the list that contains all strategies
			allStrategies.add(strategyPlayed);

			// Update the list that contains the strategies for this game
			List<GameStrategy> gameStrategies = gameToStrategiesMap.get(game);
			gameStrategies.add(strategyPlayed);

			// Update the list that contains the strategies for this game with
			// this opponent
			tempGamePayoffsAndPlayerIdPair.gamePayoffs = game;
			tempGamePayoffsAndPlayerIdPair.gamePlayerId = opponentId;

			gameStrategies = gameAndPlayerToStrategiesMap
					.get(tempGamePayoffsAndPlayerIdPair);

			gameStrategies.add(strategyPlayed);
		}

		private void addPayoff(double payoff) {
			totalPayoffs += payoff;
		}

		private double getTotalPayoff() {
			return totalPayoffs;
		}

		private GameStrategy getLastStrategy() {
			return allStrategies.isEmpty() ? null : allStrategies
					.get(allStrategies.size() - 1);
		}

		private List<GameStrategy> getAllStrategies() {
			return Collections.unmodifiableList(allStrategies);
		}

		private GameStrategy getLastStrategy(GamePayoffs game) {

			if (!gameToStrategiesMap.containsKey(game)) {
				gameToStrategiesMap.put(game, new ArrayList<GameStrategy>());
			}

			List<GameStrategy> gameStrategies = gameToStrategiesMap.get(game);

			return gameStrategies == null || gameStrategies.isEmpty() ? null
					: gameStrategies.get(gameStrategies.size() - 1);
		}

		private List<GameStrategy> getAllStrategies(GamePayoffs game) {
			if (!gameToStrategiesMap.containsKey(game)) {
				gameToStrategiesMap.put(game, new ArrayList<GameStrategy>());
			}

			List<GameStrategy> gameStrategies = gameToStrategiesMap.get(game);

			return gameStrategies == null ? null : Collections
					.unmodifiableList(gameStrategies);
		}

		private GameStrategy getLastStrategy(GamePayoffs game, UUID opponentId) {

			tempGamePayoffsAndPlayerIdPair.gamePayoffs = game;
			tempGamePayoffsAndPlayerIdPair.gamePlayerId = opponentId;

			List<GameStrategy> gameStrategies = gameAndPlayerToStrategiesMap
					.get(tempGamePayoffsAndPlayerIdPair);

			return gameStrategies == null || gameStrategies.isEmpty() ? null
					: gameStrategies.get(gameStrategies.size() - 1);
		}

		private List<GameStrategy> getAllStrategies(GamePayoffs game,
				UUID opponentId) {

			tempGamePayoffsAndPlayerIdPair.gamePayoffs = game;
			tempGamePayoffsAndPlayerIdPair.gamePlayerId = opponentId;

			List<GameStrategy> gameStrategies = gameAndPlayerToStrategiesMap
					.get(tempGamePayoffsAndPlayerIdPair);

			return gameStrategies == null ? null : Collections
					.unmodifiableList(gameStrategies);
		}
	}

	private class GamePayoffsAndPlayerIdPair {
		private GamePayoffs gamePayoffs;

		private UUID gamePlayerId;

		private GamePayoffsAndPlayerIdPair() {
			this(null, null);
		}

		private GamePayoffsAndPlayerIdPair(GamePayoffs gamePayoffs,
				UUID gamePlayerId) {
			this.gamePayoffs = gamePayoffs;
			this.gamePlayerId = gamePlayerId;
		}

		private GameHistory getOuterType() {
			return GameHistory.this;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((gamePayoffs == null) ? 0 : gamePayoffs.hashCode());
			result = prime * result
					+ ((gamePlayerId == null) ? 0 : gamePlayerId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GamePayoffsAndPlayerIdPair other = (GamePayoffsAndPlayerIdPair) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (gamePayoffs != other.gamePayoffs)
				return false;
			if (gamePlayerId == null) {
				if (other.gamePlayerId != null)
					return false;
			} else if (!gamePlayerId.equals(other.gamePlayerId))
				return false;
			return true;
		}
	}

}
