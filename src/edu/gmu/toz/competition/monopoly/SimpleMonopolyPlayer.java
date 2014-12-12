package edu.gmu.toz.competition.monopoly;

import java.util.List;

import sim.engine.SimState;

public class SimpleMonopolyPlayer extends AbstractMonopolyPlayer {

	private static final long serialVersionUID = 1L;

	private static int instanceNum = 1;

	private int playerNum = instanceNum++;

	@Override
	public void step(SimState state) {
		Monopoly monopoly = (Monopoly) state;

		// Try to purchase the space if possible
		monopoly.purchaseSpace(this);

		List<Space> properties = monopoly.getProperties(this);

		// Try to build houses
		for (Space property : properties) {
			monopoly.unmortgageProperty(this, property);

			monopoly.buildImprovement(this, property,
					Monopoly.HOUSE_IMPROVEMENT);
		}
	}

	@Override
	public boolean considerTrade(TradeProposal tradeProposal) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean considerCounter(TradeProposal tradeCounterProposal) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * String used by some of the Mason displays.
	 */
	@Override
	public String toString() {
		return "Simple Player " + playerNum;
	}
}
