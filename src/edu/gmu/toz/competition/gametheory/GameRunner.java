package edu.gmu.toz.competition.gametheory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

/**
 * Class for running the first competition for the semester. There are two
 * instances of each player created. This guarantees that there will be an even
 * number of players and gives each student two chances of winning, although
 * only one class file can be submitted for the competition. During each round
 * of competition many games will be played randomly selected from Prisoner's
 * Dilemma, Hawk-Dove, and Stag Hunt. After all games are played for a round,
 * the two players with the lowest scores will be eliminated and another round
 * will begin until
 * 
 * @author randy
 *
 */
public class GameRunner {

	public static final int NUMBER_GAMES = 100000;

	private static final URL GAME_PLAYER_URL = GameRunner.class
			.getResource("game_players.txt");

	private static List<GamePlayer> getPlayers() {
		BufferedReader in = null;
		List<GamePlayer> retval = new ArrayList<GamePlayer>();
		try {
			// Create the reader for the filename
			in = new BufferedReader(new FileReader(new File(
					GAME_PLAYER_URL.toURI())));

			// While there is still another line, then parse it
			String nextLine = null;
			while ((nextLine = in.readLine()) != null) {

				// If the line is not commented out
				if (!nextLine.startsWith("//") && !nextLine.startsWith("#")) {
					// Split it up based on commas
					String[] splitStr = nextLine.split(",");

					if (splitStr.length == 2) {
						String className = splitStr[0];
						String displayName = splitStr[1];

						Class<?> c = null;

						try {
							// See if the class can be found in this package
							c = Class.forName(GameRunner.class.getPackage()
									.getName() + "." + className);
						} catch (ClassNotFoundException e) {
							// If class not in this package, then just try the
							// class name by itself
							c = Class.forName(className);
						}

						GamePlayer gamePlayer = (GamePlayer) c.newInstance();
						gamePlayer.setDisplayName(displayName);
						retval.add(gamePlayer);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// Just print the error
			e.printStackTrace();
		} catch (IOException e) {
			// Just print the error
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// Just print the error
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// Just print the error
			e.printStackTrace();
		} catch (InstantiationException e) {
			// Just print the error
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// Just print the error
			e.printStackTrace();
		} finally {

			// Make sure to close the file
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// Just print the error
					e.printStackTrace();
				}
			}
		}

		return retval;
	}

	private static void playGame(GamePayoffs gamePayoffs,
			GameHistory gameHistory, GamePlayer player1, GamePlayer player2) {

		// Strategies used by the players
		GameStrategy strategy1 = null;
		GameStrategy strategy2 = null;

		// See if the strategy generation throws an exception
		try {
			strategy1 = player1.getGameStrategy(player2.getUniqueId(),
					gamePayoffs, gameHistory);
		} catch (Exception e) {
			// Do nothing...
			System.out.println(e.getClass() + " thrown by "
					+ player1.getDisplayName());
		}

		// See if the strategy generation throws an exception
		try {
			strategy2 = player2.getGameStrategy(player1.getUniqueId(),
					gamePayoffs, gameHistory);
		} catch (Exception e) {
			// Do nothing...
			System.out.println(e.getClass() + " thrown by "
					+ player2.getDisplayName());
		}

		// Add game to the history
		gameHistory.addStrategy(gamePayoffs, player1.getUniqueId(),
				player2.getUniqueId(), strategy1, strategy2);
	}

	public static void main(String[] args) {

		// Print the games being played in this competition
		System.out
				.println("The following games are being played in this competition:");
		for (GamePayoffs game : GamePayoffs.values()) {
			System.out.println(game);
		}

		// Used for choosing which game to play
		Random rand = new Random();

		// Used to pause between rounds (if wanted)
		Scanner scanner = new Scanner(System.in);

		// List of players
		List<GamePlayer> gamePlayers = new ArrayList<GamePlayer>();

		// Each person get two players
		// This also ensures that there are an even number of players
		gamePlayers.addAll(getPlayers());
		gamePlayers.addAll(getPlayers());

		// Number of instances of players created
		int instanceCount = AbstractGamePlayer.instanceCount;

		// Checking if additional instances are being create elsewhere
		if (instanceCount != gamePlayers.size()) {
			System.out
					.println("Instances created somewhere other than this class");
		}
		System.out.println();

		// The current round
		int round = 1;

		// Each round two players will be eliminated, so keep going as long as
		// there are at least two players left
		while (gamePlayers.size() >= 2) {

			// The following two lines cause a break between rounds
			// System.out.println("Press return to execute round " + round);
			// scanner.nextLine();

			// Create a list for the player ids that is used by the game history
			List<UUID> playerIds = new ArrayList<UUID>();

			for (GamePlayer player : gamePlayers) {
				playerIds.add(player.getUniqueId());
			}

			// Create a new game history for each round
			final GameHistory gameHistory = new GameHistory(playerIds,
					NUMBER_GAMES);

			// Play the number of games for this round
			for (int g = 0; g < NUMBER_GAMES; g++) {

				gameHistory.setNumberOfGamesPlayed(g);

				// Pick the game to play for this round
				GamePayoffs gamePayoffs = GamePayoffs.values()[rand
						.nextInt(GamePayoffs.values().length)];

				// Shuffle the players
				Collections.shuffle(gamePlayers);

				// Pair two players up to play against one another
				for (int i = 0; i < gamePlayers.size() - 1; i += 2) {

					// Players for this game
					GamePlayer player1 = gamePlayers.get(i);
					GamePlayer player2 = gamePlayers.get(i + 1);

					// Have the two players play the game
					playGame(gamePayoffs, gameHistory, player1, player2);
				}

				// Check to make sure that extra instances are not being created
				if (instanceCount != AbstractGamePlayer.instanceCount) {
					instanceCount = AbstractGamePlayer.instanceCount;
					System.out
							.println("Someone is trying to create an instance of a GamePlayer");
				}
			}

			// Sort by total payoffs
			Collections.sort(gamePlayers, new Comparator<GamePlayer>() {
				@Override
				public int compare(GamePlayer p1, GamePlayer p2) {
					return Double.compare(
							gameHistory.getTotalPayoffs(p1.getUniqueId()),
							gameHistory.getTotalPayoffs(p2.getUniqueId()));
				}
			});

			// Print results of this round
			System.out.println("Round " + round);
			for (GamePlayer player : gamePlayers) {
				System.out.println(player.getDisplayName() + " has "
						+ gameHistory.getTotalPayoffs(player.getUniqueId()));
			}
			System.out.println();

			// Increment the round
			round++;

			// Eliminate the two players with the lowest total payoff
			gamePlayers.remove(0);
			gamePlayers.remove(0);
		}

		scanner.close();
	}
}
