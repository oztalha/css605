package edu.gmu.toz.competition.monopoly;

import java.util.List;

/**
 * You can extend this class for your MonopolyPlayer and it will provide some
 * convenience methods for you to get more information about your player. Also,
 * it will enable more information about your player to be displayed in the
 * Mason displays.
 * 
 * @author randy
 *
 */
public abstract class AbstractMonopolyPlayer implements MonopolyPlayer {

	private static final long serialVersionUID = 1L;

	protected Monopoly monopoly;

	public void setMonopolyGame(Monopoly monopoly) {
		this.monopoly = monopoly;
	}

	public double getMoney() {
		return monopoly.getMoney(this);
	}

	public double getEntireWealth() {
		return monopoly.getEntireWealth(this);
	}

	public Space getSpace() {
		return monopoly.getSpace(this);
	}

	public List<Space> getProperties() {
		return monopoly.getProperties(this);
	}

	public boolean isInJail() {
		return monopoly.isInJail(this);
	}

	public boolean isBankrupt() {
		return monopoly.isBankrupt(this);
	}
}
