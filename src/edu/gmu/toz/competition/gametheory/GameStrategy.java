package edu.gmu.toz.competition.gametheory;

/**
 * An enum to define the possible strategies.
 * 
 * @author randy
 *
 */
public enum GameStrategy {

	STRATEGY1(0), STRATEGY2(1);

	// Strategy names for Prisoner's Dilemma
	public static final GameStrategy COOPERATE = GameStrategy.STRATEGY1;
	public static final GameStrategy DEFECT = GameStrategy.STRATEGY2;

	// Strategy names for Stag Hunt
	public static final GameStrategy STAG = GameStrategy.STRATEGY1;
	public static final GameStrategy HARE = GameStrategy.STRATEGY2;

	// Strategy names for Hawk-Dove (i.e., Chicken)
	public static final GameStrategy SWERVE = GameStrategy.STRATEGY1;
	public static final GameStrategy STRAIGHT = GameStrategy.STRATEGY2;

	private int index;

	/**
	 * A game strategy just takes an index for accessing the payoffs.
	 * 
	 * @param index
	 */
	private GameStrategy(int index) {
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}
}
