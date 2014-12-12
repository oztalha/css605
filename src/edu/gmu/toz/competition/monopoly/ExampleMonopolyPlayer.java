package edu.gmu.toz.competition.monopoly;

import java.util.List;

import sim.engine.SimState;

public class ExampleMonopolyPlayer extends AbstractMonopolyPlayer {

	private static final long serialVersionUID = 1L;

	private static int instanceNum = 1;

	private int playerNum = instanceNum++;

	@Override
	public void step(SimState state) {
		Monopoly monopoly = (Monopoly) state;

		// Example to show how to get the rent for new york
		Space.NEW_YORK_AVENUE.getRent(3);

		// Not really a good strategy, but the following code
		// checks if all of the other properties are available
		// before purchasing a property.

		// Assuming that other spaces are available
		boolean allOthersAvailable = true;
		Space curSpace = monopoly.getSpace(this);
		List<Space> curProperties = monopoly.getProperties(this);

		// Now, check to see if the spaces are actually available
		for (Space otherSpace : curSpace.getColorGroup().getSpaces()) {
			// If the other space is not for sale and I do not own it
			if (!monopoly.isForSale(otherSpace)
					&& !curProperties.contains(otherSpace)) {
				// Then don't buy current space
				allOthersAvailable = false;
			}
		}

		// If all the other spaces are available
		if (allOthersAvailable) {
			// Try to purchase the space if possible
			monopoly.purchaseSpace(this);
		}

		// Get the properties owned by this player
		List<Space> properties = monopoly.getProperties(this);

		// Try to build houses
		for (Space property : properties) {
			monopoly.unmortgageProperty(this, property);

			// Check if the property has less than 3 houses
			if (monopoly.getNumberOfImprovements(property) < 3) {

				// If there is less than 3 houses, then try to build
				monopoly.buildImprovement(this, property,
						Monopoly.HOUSE_IMPROVEMENT);
			}
		}

		// Calculate the number of for sale properties
		int numPropertiesStillForSale = 0;

		// Iterate over all spaces
		for (Space space : Space.values()) {
			if (monopoly.isForSale(space)) {
				// Count number of properties for sale
				numPropertiesStillForSale++;
			}
		}

		// Print out the number of properties that are still for sale
		// System.err.println("properties still for sale: "
		// + numPropertiesStillForSale);

		// Propose a trade
		proposeTrade(monopoly);
	}

	private void proposeTrade(Monopoly monopoly) {
		// Get the properties of this player
		List<Space> props = monopoly.getProperties(this);

		// Iterate through the player's properties
		for (Space s : props) {
			Space neededSpace = needOnlyOneForSet(s);

			if (neededSpace != null) {
				// Propose trade!!
				monopoly.proposeTrade(this, getMoney(), null, 0, neededSpace);
			}
		}
	}

	// Returns space that you need
	private Space needOnlyOneForSet(Space space) {
		// You need to implement this..
		return null;
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
		return "Example Player " + playerNum;
	}
}
