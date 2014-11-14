package edu.gmu.toz.competition.gametheory;

import java.util.Arrays;

/**
 * An enum to define the payoffs for each of the games:
 * 
 * Prisoner's Dilemma, Stag Hunt, and Hawk-Dove (Chicken)
 * 
 * @author randy
 *
 */
public enum GamePayoffs {

	PRISONERS_DILEMMA(new double[][] { { 1.0, -1.0 }, { 2.0, 0.0 } }),

	STAG_HUNT(new double[][] { { 1.0, 0.0 }, { 0.6, 0.4 } }),

	HAWK_DOVE(new double[][] { { 1.0, 0.0 }, { 2.0, -1.0 } }),

	// Added two games for Week 10 Assignment
	COORDINATION(new double[][] { { 2.0, -1.0 }, { 0.0, 1.0 } }),

	ANTI_COORDINATION(new double[][] { { 0.0, 1.0 }, { 2.0, -1.0 } });

	// Another name for the hawk-dove game is chicken
	public static GamePayoffs CHICKEN = HAWK_DOVE;

	private double[][] payoffs;

	/**
	 * Since all of the games are symmetric, only one set of payoffs are needed.
	 * 
	 * @param payoffs
	 */
	private GamePayoffs(double[][] payoffs) {
		this.payoffs = payoffs;
	}

	/**
	 * Returns the payoff based on the strategies of the two players. If both
	 * players submit a legitimate strategy, then the payoff is a simple lookup
	 * in the payoff two-dimensional array. However, if myStrategy is null, then
	 * the payoff is the minimum possible payoff for this game. If the
	 * otherStrategy is null, then the payoff is the maximum possible payoff for
	 * this game.
	 * 
	 * @param myStrategy
	 * @param otherStrategy
	 * @return
	 */
	public double getPayoff(GameStrategy myStrategy, GameStrategy otherStrategy) {
		if (myStrategy != null && otherStrategy != null) {
			// If both strategies are not null, then just return the payoff
			return payoffs[myStrategy.getIndex()][otherStrategy.getIndex()];
		} else if (myStrategy == null) {
			// If myStrategy is null, then you get the minimum payoff
			return Math.min(Math.min(payoffs[0][0], payoffs[0][1]),
					Math.min(payoffs[1][0], payoffs[1][1]));
		} else {
			// Else the other strategy is null, so you get the maximum payoff
			return Math.max(Math.max(payoffs[0][0], payoffs[0][1]),
					Math.max(payoffs[1][0], payoffs[1][1]));
		}
	}

	@Override
	public String toString() {
		return name() + "\n" + Arrays.toString(payoffs[0]) + "\n"
				+ Arrays.toString(payoffs[1]);
	}
}
