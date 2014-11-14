package edu.gmu.toz.competition.gametheory;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class GameHistoryTest {

	private int gamesToBePlayed = 15;

	private int gamesToBePlayedOfEachGame = 5;

	private int numPlayers = 10;

	private List<UUID> playerIds;

	private GameHistory gameHistory;

	private List<GameStrategy> allStrategiesEven;

	private List<GameStrategy> allStrategiesOdd;

	private List<GameStrategy> pdStrategiesEven;

	private List<GameStrategy> pdStrategiesOdd;

	private List<GameStrategy> hdStrategiesEven;

	private List<GameStrategy> hdStrategiesOdd;

	private List<GameStrategy> shStrategiesEven;

	private List<GameStrategy> shStrategiesOdd;

	@Before
	public void setup() {

		playerIds = new ArrayList<UUID>();

		for (int i = 0; i < numPlayers; i++) {
			playerIds.add(new UUID(0, i));
		}

		gameHistory = new GameHistory(playerIds, gamesToBePlayed);

		allStrategiesEven = new ArrayList<GameStrategy>();
		allStrategiesOdd = new ArrayList<GameStrategy>();

		pdStrategiesEven = new ArrayList<GameStrategy>();
		pdStrategiesOdd = new ArrayList<GameStrategy>();

		hdStrategiesEven = new ArrayList<GameStrategy>();
		hdStrategiesOdd = new ArrayList<GameStrategy>();

		shStrategiesEven = new ArrayList<GameStrategy>();
		shStrategiesOdd = new ArrayList<GameStrategy>();

		for (int r = 0; r < gamesToBePlayedOfEachGame; r++) {
			for (int i = 0; i < numPlayers - 1; i += 2) {
				gameHistory.addStrategy(GamePayoffs.STAG_HUNT,
						playerIds.get(i), playerIds.get(i + 1),
						GameStrategy.STRATEGY2, GameStrategy.STRATEGY1);
			}
			allStrategiesEven.add(GameStrategy.STRATEGY2);
			allStrategiesOdd.add(GameStrategy.STRATEGY1);

			shStrategiesEven.add(GameStrategy.STRATEGY2);
			shStrategiesOdd.add(GameStrategy.STRATEGY1);

			for (int i = 0; i < numPlayers - 1; i += 2) {
				gameHistory.addStrategy(GamePayoffs.HAWK_DOVE,
						playerIds.get(i), playerIds.get(i + 1),
						GameStrategy.STRATEGY2, GameStrategy.STRATEGY2);
			}
			allStrategiesEven.add(GameStrategy.STRATEGY2);
			allStrategiesOdd.add(GameStrategy.STRATEGY2);

			hdStrategiesEven.add(GameStrategy.STRATEGY2);
			hdStrategiesOdd.add(GameStrategy.STRATEGY2);

			for (int i = 0; i < numPlayers - 1; i += 2) {
				gameHistory.addStrategy(GamePayoffs.PRISONERS_DILEMMA,
						playerIds.get(i), playerIds.get(i + 1),
						GameStrategy.STRATEGY1, GameStrategy.STRATEGY2);
			}
			allStrategiesEven.add(GameStrategy.STRATEGY1);
			allStrategiesOdd.add(GameStrategy.STRATEGY2);

			pdStrategiesEven.add(GameStrategy.STRATEGY1);
			pdStrategiesOdd.add(GameStrategy.STRATEGY2);

		}
	}

	@Test
	public void testGetLastStrategyOfPlayer() {
		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect last strategy",
					pdStrategiesEven.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i)));
			assertEquals("Incorrect last strategy",
					pdStrategiesOdd.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i + 1)));
		}
	}

	@Test
	public void testGetAllStrategiesOfPlayer() {
		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect all strategies", allStrategiesEven,
					gameHistory.getAllStrategies(playerIds.get(i)));
			assertEquals("Incorrect all strategies", allStrategiesOdd,
					gameHistory.getAllStrategies(playerIds.get(i + 1)));
		}
	}

	@Test
	public void testGetLastStrategyOfPlayerAndGame() {
		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect last strategy for game",
					pdStrategiesEven.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i),
							GamePayoffs.PRISONERS_DILEMMA));
			assertEquals("Incorrect last strategy for game",
					pdStrategiesOdd.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i + 1),
							GamePayoffs.PRISONERS_DILEMMA));
		}

		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect last strategy for game",
					shStrategiesEven.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i),
							GamePayoffs.STAG_HUNT));
			assertEquals("Incorrect last strategy for game",
					shStrategiesOdd.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i + 1),
							GamePayoffs.STAG_HUNT));
		}

		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect last strategy for game",
					hdStrategiesEven.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i),
							GamePayoffs.HAWK_DOVE));
			assertEquals("Incorrect last strategy for game",
					hdStrategiesOdd.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i + 1),
							GamePayoffs.HAWK_DOVE));
		}
	}

	@Test
	public void testGetAllStrategiesOfPlayerAndGame() {
		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect all strategies for game", pdStrategiesEven,
					gameHistory.getAllStrategies(playerIds.get(i),
							GamePayoffs.PRISONERS_DILEMMA));
			assertEquals("Incorrect all strategies for game", pdStrategiesOdd,
					gameHistory.getAllStrategies(playerIds.get(i + 1),
							GamePayoffs.PRISONERS_DILEMMA));
		}

		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect all strategies for game", shStrategiesEven,
					gameHistory.getAllStrategies(playerIds.get(i),
							GamePayoffs.STAG_HUNT));
			assertEquals("Incorrect all strategies for game", shStrategiesOdd,
					gameHistory.getAllStrategies(playerIds.get(i + 1),
							GamePayoffs.STAG_HUNT));
		}

		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect all strategies for game", hdStrategiesEven,
					gameHistory.getAllStrategies(playerIds.get(i),
							GamePayoffs.HAWK_DOVE));
			assertEquals("Incorrect all strategies for game", hdStrategiesOdd,
					gameHistory.getAllStrategies(playerIds.get(i + 1),
							GamePayoffs.HAWK_DOVE));
		}
	}

	@Test
	public void testGetLastStrategyOfPlayerAndGameWithOpponent() {
		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals(
					"Incorrect last strategy for game with opponent",
					pdStrategiesEven.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i),
							GamePayoffs.PRISONERS_DILEMMA, playerIds.get(i + 1)));
			assertEquals("Incorrect last strategy for game with opponent",
					pdStrategiesOdd.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i + 1),
							GamePayoffs.PRISONERS_DILEMMA, playerIds.get(i)));
		}

		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect last strategy for game with opponent",
					shStrategiesEven.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i),
							GamePayoffs.STAG_HUNT, playerIds.get(i + 1)));
			assertEquals("Incorrect last strategy for game with opponent",
					shStrategiesOdd.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i + 1),
							GamePayoffs.STAG_HUNT, playerIds.get(i)));
		}

		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect last strategy for game with opponent",
					hdStrategiesEven.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i),
							GamePayoffs.HAWK_DOVE, playerIds.get(i + 1)));
			assertEquals("Incorrect last strategy for game with opponent",
					hdStrategiesOdd.get(gamesToBePlayedOfEachGame - 1),
					gameHistory.getLastStrategy(playerIds.get(i + 1),
							GamePayoffs.HAWK_DOVE, playerIds.get(i)));
		}

		assertNull("Incorrect last strategy for game with opponent",
				gameHistory.getLastStrategy(playerIds.get(1),
						GamePayoffs.HAWK_DOVE, playerIds.get(3)));

		gameHistory.addStrategy(GamePayoffs.HAWK_DOVE, playerIds.get(1),
				playerIds.get(3), GameStrategy.STRATEGY1,
				GameStrategy.STRATEGY2);

		gameHistory.addStrategy(GamePayoffs.HAWK_DOVE, playerIds.get(1),
				playerIds.get(4), GameStrategy.STRATEGY2,
				GameStrategy.STRATEGY1);

		assertEquals("Incorrect last strategy for game with opponent",
				GameStrategy.STRATEGY1, gameHistory.getLastStrategy(
						playerIds.get(1), GamePayoffs.HAWK_DOVE,
						playerIds.get(3)));

		assertEquals("Incorrect last strategy for game with opponent",
				GameStrategy.STRATEGY2, gameHistory.getLastStrategy(
						playerIds.get(1), GamePayoffs.HAWK_DOVE,
						playerIds.get(4)));

		assertEquals("Incorrect last strategy for game with opponent",
				GameStrategy.STRATEGY2, gameHistory.getLastStrategy(
						playerIds.get(3), GamePayoffs.HAWK_DOVE,
						playerIds.get(1)));

		assertEquals("Incorrect last strategy for game with opponent",
				GameStrategy.STRATEGY1, gameHistory.getLastStrategy(
						playerIds.get(4), GamePayoffs.HAWK_DOVE,
						playerIds.get(1)));
	}

	@Test
	public void testGetAllStrategiesOfPlayerAndGameWithOpponent() {
		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect all strategies for game with opponent",
					pdStrategiesEven, gameHistory.getAllStrategies(
							playerIds.get(i), GamePayoffs.PRISONERS_DILEMMA,
							playerIds.get(i + 1)));
			assertEquals("Incorrect all strategies for game with opponent",
					pdStrategiesOdd, gameHistory.getAllStrategies(
							playerIds.get(i + 1),
							GamePayoffs.PRISONERS_DILEMMA, playerIds.get(i)));
		}

		assertEquals("Incorrect all strategies for game with opponent",
				new ArrayList<GameStrategy>(), gameHistory.getAllStrategies(
						playerIds.get(0), GamePayoffs.PRISONERS_DILEMMA,
						playerIds.get(5)));

		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect all strategies for game with opponent",
					shStrategiesEven, gameHistory.getAllStrategies(
							playerIds.get(i), GamePayoffs.STAG_HUNT,
							playerIds.get(i + 1)));
			assertEquals("Incorrect all strategies for game with opponent",
					shStrategiesOdd, gameHistory.getAllStrategies(
							playerIds.get(i + 1), GamePayoffs.STAG_HUNT,
							playerIds.get(i)));
		}

		assertEquals("Incorrect all strategies for game with opponent",
				new ArrayList<GameStrategy>(), gameHistory.getAllStrategies(
						playerIds.get(1), GamePayoffs.STAG_HUNT,
						playerIds.get(4)));

		for (int i = 0; i < numPlayers - 1; i += 2) {
			assertEquals("Incorrect all strategies for game with opponent",
					hdStrategiesEven, gameHistory.getAllStrategies(
							playerIds.get(i), GamePayoffs.HAWK_DOVE,
							playerIds.get(i + 1)));
			assertEquals("Incorrect all strategies for game with opponent",
					hdStrategiesOdd, gameHistory.getAllStrategies(
							playerIds.get(i + 1), GamePayoffs.HAWK_DOVE,
							playerIds.get(i)));
		}

		assertEquals("Incorrect all strategies for game with opponent",
				new ArrayList<GameStrategy>(), gameHistory.getAllStrategies(
						playerIds.get(1), GamePayoffs.HAWK_DOVE,
						playerIds.get(3)));

		gameHistory.addStrategy(GamePayoffs.HAWK_DOVE, playerIds.get(1),
				playerIds.get(3), GameStrategy.STRATEGY1,
				GameStrategy.STRATEGY2);

		gameHistory.addStrategy(GamePayoffs.HAWK_DOVE, playerIds.get(1),
				playerIds.get(3), GameStrategy.STRATEGY2,
				GameStrategy.STRATEGY1);

		assertArrayEquals(
				"Incorrect all strategies for game with opponent",
				new GameStrategy[] { GameStrategy.STRATEGY1,
						GameStrategy.STRATEGY2 },
				gameHistory.getAllStrategies(playerIds.get(1),
						GamePayoffs.HAWK_DOVE, playerIds.get(3)).toArray(
						new GameStrategy[2]));

		assertArrayEquals(
				"Incorrect all strategies for game with opponent",
				new GameStrategy[] { GameStrategy.STRATEGY2,
						GameStrategy.STRATEGY1 },
				gameHistory.getAllStrategies(playerIds.get(3),
						GamePayoffs.HAWK_DOVE, playerIds.get(1)).toArray(
						new GameStrategy[2]));

	}
}
