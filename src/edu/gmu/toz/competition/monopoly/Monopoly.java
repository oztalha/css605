/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package edu.gmu.toz.competition.monopoly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.grid.ObjectGrid2D;
import sim.field.grid.SparseGrid2D;
import sim.util.Bag;

public class Monopoly extends SimState {
	private static final long serialVersionUID = 1;

	public static final double MONEY_START = 1500.0;

	public static final double MONEY_PASS_GO = 200.0;

	public static final double MONEY_GET_OUT_OF_JAIL = 50.0;

	public static final int HOUSE_IMPROVEMENT = 1;

	public static final int HOTEL_IMPROVEMENT = 5;

	public static final int MAX_TRADE_PROPOSALS_PER_TURN = 1000;

	public final int boardHeight = 11;

	public final int boardWidth = 11;

	public ObjectGrid2D gameBoardGrid;

	public SparseGrid2D playerLocationGrid;

	private List<MonopolyPlayer> players = new ArrayList<MonopolyPlayer>();

	private List<MonopolyPlayer> playersInJail = new ArrayList<MonopolyPlayer>();

	private List<MonopolyPlayer> playersInBankruptcy = new ArrayList<MonopolyPlayer>();

	private Map<MonopolyPlayer, MonopolyPlayerRecord> playerToRecordMap = new HashMap<MonopolyPlayer, MonopolyPlayerRecord>();

	private Map<Space, SpaceRecord> spaceToRecordMap = new HashMap<Space, SpaceRecord>();

	// The player who turn it is now
	private MonopolyPlayer currentPlayer;

	private MonopolyPlayer lastPlayerToReceiveMoney;

	private boolean tradeInProgress = false;

	private int tradeProposalsThisTurn = 0;

	/** Creates a Monopoly simulation with the given random number seed. */
	public Monopoly(long seed) {
		super(seed);
		createGrids();
	}

	//
	//
	//
	//
	// The following public methods are available to the MonopolyPlayers when
	// making decisions about what to do.
	//
	//
	//
	//

	/**
	 * Returns all of the active players in the game (players that are not in
	 * bankruptcy).
	 * 
	 * @return
	 */
	public List<MonopolyPlayer> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	/**
	 * Returns all of the bankrupt players that are no longer in the game.
	 * 
	 * This method will probably not be of help when creating your Monopoly
	 * player, but is used for displaying the game.
	 * 
	 * @return
	 */
	public List<MonopolyPlayer> getPlayersInBankruptcy() {
		return Collections.unmodifiableList(playersInBankruptcy);
	}

	/**
	 * Returns the number of players remaining in the game.
	 * 
	 * @return
	 */
	public int getNumberOfPlayers() {
		return players.size();
	}

	/**
	 * Returns the number of players that are bankrupt.
	 * 
	 * @return
	 */
	public int getNumberOfBankruptPlayers() {
		return playersInBankruptcy.size();
	}

	/**
	 * Returns the amount of money the specified player has.
	 * 
	 * @param player
	 * @return
	 */
	public double getMoney(MonopolyPlayer player) {
		return playerToRecordMap.get(player).getMoney();
	}

	/**
	 * Returns the entire wealth (properties, houses, hotels, and money) of the
	 * specified player.
	 * 
	 * @param player
	 * @return
	 */
	public double getEntireWealth(MonopolyPlayer player) {
		return playerToRecordMap.get(player).getEntireWealth();
	}

	/**
	 * Returns the current space of the specified player.
	 * 
	 * @param player
	 * @return
	 */
	public Space getSpace(MonopolyPlayer player) {
		return playerToRecordMap.get(player).getSpace();
	}

	/**
	 * Returns a list of properties that are owned by the player.
	 * 
	 * @param player
	 * @return
	 */
	public List<Space> getProperties(MonopolyPlayer player) {
		return playerToRecordMap.get(player).getProperties();
	}

	/**
	 * Returns whether or not the player is in jail.
	 * 
	 * @param player
	 * @return
	 */
	public boolean isInJail(MonopolyPlayer player) {
		return playersInJail.contains(player);
	}

	/**
	 * Returns whether or not the player is bankrupt.
	 * 
	 * @param player
	 * @return
	 */
	public boolean isBankrupt(MonopolyPlayer player) {
		return playersInBankruptcy.contains(player);
	}

	/**
	 * Allows player to pay to get out of jail. Does nothing if the player is
	 * not in jail.
	 * 
	 * Returns whether or not the player successfully paid to get out of jail.
	 * This would return false if the player did not have enough money to pay to
	 * get out of jail or if the player was not in jail in the first place.
	 * 
	 * @param player
	 * @return
	 */
	public boolean payToGetOutOfJail(MonopolyPlayer player) {
		if (!operationAllowed(player) || !isInJail(player)
				|| getMoney(player) < MONEY_GET_OUT_OF_JAIL) {
			return false;
		}

		if (isInJail(player)) {
			playersInJail.remove(player);
			addMoney(player, -MONEY_GET_OUT_OF_JAIL);
			return true;
		}

		return false;
	}

	/**
	 * Returns whether or not the space (property) is for sale.
	 * 
	 * @param space
	 * @return
	 */
	public boolean isForSale(Space space) {
		// If the space is not a property that can be sold, then just return
		if (space == null || space.getPrice() == Double.MAX_VALUE) {
			return false;
		}

		return spaceToRecordMap.get(space).getOwner() == null;
	}

	/**
	 * Returns whether or not the space (property) has been mortgaged.
	 * 
	 * @param space
	 * @return
	 */
	public boolean isMortgaged(Space space) {
		// If the space is not a property that can be sold, then just return
		if (space == null || space.getPrice() == Double.MAX_VALUE) {
			return false;
		}

		return spaceToRecordMap.get(space).isMortgaged();
	}

	/**
	 * Returns the number of improvements (houses or hotels) for the provided
	 * space. For example, if there are three houses, then it will return 3. If
	 * the property has a hotel, then it will return 5.
	 * 
	 * @param space
	 * @return
	 */
	public int getNumberOfImprovements(Space space) {
		// If the space is not a property that can be sold, then return zero
		if (space == null || space.getPrice() == Double.MAX_VALUE) {
			return 0;
		}

		return spaceToRecordMap.get(space).getNumImprovements();
	}

	/**
	 * Returns the number of times the current player has proposed a trade
	 * during this turn.
	 * 
	 * @return
	 */
	public int getTradeProposalsThisTurn() {
		return tradeProposalsThisTurn;
	}

	/**
	 * Purchases the space if possible.
	 * 
	 * Returns true if the transactions went through and otherwise false.
	 * 
	 * @param player
	 * @return whether or not the operation was successful
	 */
	public boolean purchaseSpace(MonopolyPlayer player) {

		if (!operationAllowed(player)) {
			return false;
		}

		MonopolyPlayerRecord playerRecord = playerToRecordMap.get(player);

		// Find the current location of the player
		Space space = playerRecord.getSpace();

		// If the player has the money to buy the space and it is for sale
		if (isForSale(space) && playerRecord.getMoney() >= space.getPrice()) {

			// It is okay for player to purchase
			playerRecord.purchaseProperty(space);

			return true;
		}

		// The transaction did not go through
		return false;
	}

	/**
	 * Build houses or hotels.
	 * 
	 * If you want to build a hotel, then set the amount of improvement to 5.
	 * 
	 * @param player
	 *            the player doing the building
	 * @param property
	 *            the space to build the houses
	 * @param amountOfImprovement
	 *            number of houses to build
	 * @return whether or not the operation was successful
	 */
	public boolean buildImprovement(MonopolyPlayer player, Space property,
			int amountOfImprovement) {
		if (!operationAllowed(player)) {
			return false;
		}

		SpaceRecord spaceRecord = spaceToRecordMap.get(property);

		// If no further improvements are possible
		if (spaceRecord.getOwner() != player
				|| !spaceRecord.isMoreImprovementsPossible(amountOfImprovement)) {
			return false;
		}

		// Make sure player owns entire set and none of them are mortgaged
		for (Space colorSpace : property.getColorGroup().getSpaces()) {
			SpaceRecord colorSpaceRecord = spaceToRecordMap.get(colorSpace);

			if (colorSpaceRecord.getOwner() != player
					|| colorSpaceRecord.isMortgaged()) {
				return false;
			}
		}

		double improvementCost = property.getBuildingCost()
				* amountOfImprovement;

		// If the player does not have the money to make the improvement
		if (property.getBuildingCost() == Double.MAX_VALUE
				|| playerToRecordMap.get(player).getMoney() < improvementCost) {
			return false;
		}

		return spaceRecord.makeImprovement(amountOfImprovement);
	}

	/**
	 * Method for selling houses or hotels.
	 * 
	 * @param player
	 *            the owner of the property
	 * @param property
	 *            the property with the houses or hotels
	 * @param amountToSell
	 *            the number of improvements to sell
	 * @return whether or not the operation was successful
	 */
	public boolean sellImprovement(MonopolyPlayer player, Space property,
			int amountToSell) {
		if (!operationAllowed(player)) {
			return false;
		}

		SpaceRecord spaceRecord = spaceToRecordMap.get(property);

		// If no further improvements are possible
		if (spaceRecord.getOwner() != player
				|| spaceRecord.getNumImprovements() < amountToSell) {
			return false;
		}

		return spaceRecord.sellImprovement(amountToSell);
	}

	/**
	 * Method for mortgaging a property.
	 * 
	 * @param player
	 *            the owner of the property
	 * @param property
	 *            the property to mortgage
	 * @return whether or not the operation was successful
	 */
	public boolean mortgageProperty(MonopolyPlayer player, Space property) {
		if (!operationAllowed(player)) {
			return false;
		}

		SpaceRecord propertyRecord = spaceToRecordMap.get(property);

		// Cannot mortgage property if you do not own it, if it is mortgaged
		// already, or if it has houses on it
		if (propertyRecord.getOwner() != player || propertyRecord.isMortgaged()
				|| propertyRecord.getNumImprovements() > 0) {
			return false;
		}

		return propertyRecord.mortgageProperty();
	}

	/**
	 * Method for paying the mortgage on a property. The cost for paying a
	 * mortgage is 10% more than the mortgaged value.
	 * 
	 * @param player
	 *            the owner of the property
	 * @param property
	 *            the property that is being un-mortgaged
	 * @return whether or not the operation was successful
	 */
	public boolean unmortgageProperty(MonopolyPlayer player, Space property) {
		if (!operationAllowed(player)) {
			return false;
		}

		SpaceRecord propertyRecord = spaceToRecordMap.get(property);

		// Only can remove mortgage from property if you own it and it has been
		// mortgaged
		if (propertyRecord.getOwner() != player
				|| !propertyRecord.isMortgaged()) {
			return false;
		}

		return propertyRecord.unmortgageProperty();
	}

	/**
	 * Proposes a trade to the other players.
	 * 
	 * @param player
	 *            the player making the proposal
	 * @param offeredMoney
	 *            the amount of money the player is offering (could be zero)
	 * @param offeredProperty
	 *            the property the player is offering (could be null)
	 * @param requestedMoney
	 *            the requested money by the player (could be zero)
	 * @param requestedProperty
	 *            the requested property by the player (could be null)
	 * @return whether or not the operation was successful
	 */
	public boolean proposeTrade(MonopolyPlayer player, double offeredMoney,
			Space offeredProperty, double requestedMoney,
			Space requestedProperty) {

		if (!operationAllowed(player)) {
			return false;
		}

		List<Space> offeredProperties = new ArrayList<Space>();
		if (offeredProperty != null) {
			offeredProperties.add(offeredProperty);
		}

		List<Space> requestedProperties = new ArrayList<Space>();
		if (requestedProperty != null) {
			requestedProperties.add(requestedProperty);
		}

		return proposeTrade(player, offeredMoney, offeredProperties,
				requestedMoney, requestedProperties);
	}

	/**
	 * Same as the method above, but allows you to offer and request multiple
	 * properties instead of just one.
	 * 
	 * @param player
	 *            the player making the proposal
	 * @param offeredMoney
	 *            the amount of money the player is offering (could be zero)
	 * @param offeredProperties
	 *            the properties the player is offering (could be null)
	 * @param requestedMoney
	 *            the requested money by the player (could be zero)
	 * @param requestedProperties
	 *            the requested properties by the player (could be null)
	 * @return whether or not the operation was successful
	 */
	public boolean proposeTrade(MonopolyPlayer player, double offeredMoney,
			List<Space> offeredProperties, double requestedMoney,
			List<Space> requestedProperties) {

		if (!operationAllowed(player)) {
			return false;
		}

		// Increment the number of trade proposals
		tradeProposalsThisTurn++;

		// If the player has made too many trade proposals then just return
		if (tradeProposalsThisTurn > MAX_TRADE_PROPOSALS_PER_TURN) {
			return false;
		}

		MonopolyPlayerRecord playerRecord = playerToRecordMap.get(player);

		// Must have the money to offer it
		if (playerRecord.getMoney() < offeredMoney) {
			return false;
		}

		// If offered properties is null, then make it an empty list
		if (offeredProperties == null) {
			offeredProperties = new ArrayList<Space>();
		}

		for (Space offeredProperty : offeredProperties) {
			// You must own the property to offer it
			if (spaceToRecordMap.get(offeredProperty).getOwner() != player) {
				return false;
			}

			// Cannot trade property that has been improved.
			// This avoids the problem of a set being broken up and there being
			// houses on it.
			if (spaceToRecordMap.get(offeredProperty).getNumImprovements() > 0) {
				return false;
			}
		}

		// If requested properties is null, then make it an empty list
		if (requestedProperties == null) {
			requestedProperties = new ArrayList<Space>();
		}

		for (Space requestedProperty : requestedProperties) {
			// Cannot trade property that has been improved.
			// This avoids the problem of a set being broken up and there being
			// houses on it.
			if (spaceToRecordMap.get(requestedProperty).getNumImprovements() > 0) {
				return false;
			}
		}

		// Limits the operations possible while a trade is in progress
		tradeInProgress = true;

		// Let the player consider the trade in random order
		Bag shuffledPlayers = new Bag(players);

		// No point in offering a proposal to the proposer
		shuffledPlayers.remove(player);

		// Shuffle using MASON random so the run can be reproducible
		shuffledPlayers.shuffle(random);

		// Create the original trade proposal instance
		TradeProposal proposal = new TradeProposal(offeredMoney,
				offeredProperties, requestedMoney, requestedProperties);

		// Offer the proposal to each of the players in random order
		for (Object otherPlayerObject : shuffledPlayers) {
			MonopolyPlayer otherPlayer = (MonopolyPlayer) otherPlayerObject;

			// Make a copy of the proposal so the player considering it can make
			// whatever changes it likes for the counter proposal
			TradeProposal proposalCopy = new TradeProposal(proposal);

			// See if the other player accepts or rejects the proposal
			boolean acceptsProposal = otherPlayer.considerTrade(proposalCopy);

			// Allow one counter proposal
			if (acceptsProposal) {
				// If the trade proposal is accepted as is, then process trade
				if (!proposalCopy.isTradedAltered()) {
					// If the trade is process without any problems then return
					if (processTrade(player, otherPlayer, proposal)) {
						tradeInProgress = false;
						return true;
					}
				} else {
					// Since the other player changed the proposal, make a copy
					// of the changed proposal
					TradeProposal proposalCopyCopy = new TradeProposal(
							proposalCopy);

					// See if the original proposer accepts the counter proposal
					boolean acceptsCounterProposal = player
							.considerTrade(proposalCopyCopy);

					// If the proposer accepts the proposal as is, then process
					// trade
					if (acceptsCounterProposal
							&& !proposalCopyCopy.isTradedAltered()) {
						// If the trade is processed without problems then
						// return
						if (processTrade(player, otherPlayer, proposalCopy)) {
							tradeInProgress = false;
							return true;
						}
					}
				}
			}
		}

		tradeInProgress = false;
		return false;
	}

	//
	//
	//
	//
	//
	// All of the following methods are for running the simulation and cannot be
	// called by a MonopolyPlayer.
	//
	//
	//
	//
	//

	/**
	 * This method is called after both players accept the trade proposal.
	 * 
	 * Returns true if the trade was successfully completed.
	 * 
	 * @param proposePlayer
	 *            the player that made the original proposal
	 * @param acceptPlayer
	 *            the player that accepted the proposal
	 * @param tradeProposal
	 *            the details of the trade
	 * @return whether or not the operation was successful
	 */
	private boolean processTrade(MonopolyPlayer proposePlayer,
			MonopolyPlayer acceptPlayer, TradeProposal tradeProposal) {

		// Make sure propose player has the necessary assets for trade to go
		// through
		MonopolyPlayerRecord proposePlayerRecord = playerToRecordMap
				.get(proposePlayer);
		if (proposePlayerRecord.getMoney() < tradeProposal.getOfferedMoney()) {
			return false;
		}

		if (!proposePlayerRecord.getProperties().containsAll(
				tradeProposal.getOfferedProperties())) {
			return false;
		}

		// Make sure accept player has the necessary assets for trade to go
		// through
		MonopolyPlayerRecord acceptPlayerRecord = playerToRecordMap
				.get(acceptPlayer);
		if (acceptPlayerRecord.getMoney() < tradeProposal.getRequestedMoney()) {
			return false;
		}

		if (!acceptPlayerRecord.getProperties().containsAll(
				tradeProposal.getRequestedProperties())) {
			return false;
		}

		// Each player gets their money
		proposePlayerRecord.addMoney(tradeProposal.getRequestedMoney());
		acceptPlayerRecord.addMoney(tradeProposal.getOfferedMoney());

		// Each player pays their money
		proposePlayerRecord.addMoney(-tradeProposal.getOfferedMoney());
		acceptPlayerRecord.addMoney(-tradeProposal.getRequestedMoney());

		// Move the properties
		for (Space property : tradeProposal.getRequestedProperties()) {
			acceptPlayerRecord.removeProperty(property);
			proposePlayerRecord.addProperty(property);
		}

		// Move the properties the other way
		for (Space property : tradeProposal.getOfferedProperties()) {
			proposePlayerRecord.removeProperty(property);
			acceptPlayerRecord.addProperty(property);
		}

		return true;
	}

	/**
	 * Creates the grids for the game board and the player locations.
	 * 
	 */
	private void createGrids() {
		gameBoardGrid = new ObjectGrid2D(boardWidth, boardHeight);

		for (Space space : Space.values()) {
			SpaceRecord spaceRecord = new SpaceRecord(space);
			gameBoardGrid.set(space.getXLoc(), space.getYLoc(), spaceRecord);
			spaceToRecordMap.put(space, spaceRecord);
		}

		playerLocationGrid = new SparseGrid2D(boardWidth, boardHeight);
	}

	/**
	 * Clears all everything from previous simulation run, so the simulation can
	 * be executed again.
	 * 
	 */
	private void clearAll() {
		players.clear();
		playersInJail.clear();
		playersInBankruptcy.clear();
		playerToRecordMap.clear();
		spaceToRecordMap.clear();
	}

	/**
	 * Resets and starts a simulation
	 */
	public void start() {
		super.start(); // clear out the schedule

		clearAll();

		// Make new grids for game board and agent locations
		createGrids();

		List<MonopolyPlayer> playersFromFile = createPlayersFromFile(MONOPOLY_PLAYER_FILE);

		// Schedule the players
		for (MonopolyPlayer player : playersFromFile) {

			// If you extend the abstract player, then set up some convenience
			// methods
			if (player instanceof AbstractMonopolyPlayer) {
				((AbstractMonopolyPlayer) player).setMonopolyGame(this);
			}

			MonopolyPlayerRecord playerRecord = new MonopolyPlayerRecord(player);

			playerLocationGrid.setObjectLocation(player, Space.GO.getXLoc(),
					Space.GO.getYLoc());
			playerToRecordMap.put(player, playerRecord);
			players.add(player);
			schedule.scheduleRepeating(playerRecord);
		}

		// After each player has a turn, check to see if the game is over
		schedule.scheduleRepeating(new Steppable() {

			private static final long serialVersionUID = 1L;

			@Override
			public void step(SimState state) {
				// If there is only one player left, then the game is over
				if (players.size() == 1) {
					kill();
				}
			}
		}, 10, 1);
	}

	/**
	 * After the player rolls the dice, this method takes care of any
	 * record-keeping that needs to take place (e.g., drawing a community chest
	 * or chance card or paying rent).
	 * 
	 * @param player
	 */
	private void handleNextSpace(MonopolyPlayer player) {

		Space space = playerToRecordMap.get(player).getSpace();
		ColorGroup spaceGroup = space.getColorGroup();

		switch (spaceGroup) {
		case COMMUNITY_CHEST:
			// If the players roll is not over
			if (!drawCommunityChestCard(player)) {
				handleNextSpace(player);
			}
			return;
		case CHANCE:
			// If the players roll is not over
			if (!drawChanceCard(player)) {
				handleNextSpace(player);
			}
			return;
		case UTILITY:

			// Do nothing if mortgaged
			if (spaceToRecordMap.get(space).isMortgaged) {
				return;
			}

			MonopolyPlayer utilityOwner = spaceToRecordMap.get(space)
					.getOwner();
			// If the player needs to pay money to utility owner
			if (utilityOwner != null && utilityOwner != player) {

				// See how many utilities are owned by the owner
				int utilitiesOwned = 0;

				for (Space utility : ColorGroup.UTILITY.getSpaces()) {
					if (spaceToRecordMap.get(utility).getOwner() == utilityOwner) {
						utilitiesOwned++;
					}
				}

				// Calculate the amount to charge for the utility
				double utilityAmount = simpleRollDice()
						* space.getRent(utilitiesOwned);
				transferMoney(player, utilityOwner, utilityAmount);
			}
			return;
		case RAILROAD:

			// Do nothing if mortgaged
			if (spaceToRecordMap.get(space).isMortgaged) {
				return;
			}

			MonopolyPlayer railroadOwner = spaceToRecordMap.get(space)
					.getOwner();
			// If the player needs to pay money to utility owner
			if (railroadOwner != null && railroadOwner != player) {

				// See how many utilities are owned by the owner
				int railroadsOwned = 0;

				for (Space railroad : ColorGroup.RAILROAD.getSpaces()) {
					if (spaceToRecordMap.get(railroad).getOwner() == railroadOwner) {
						railroadsOwned++;
					}
				}

				// Calculate the amount to charge for the utility
				double railroadAmount = space.getRent(railroadsOwned);
				transferMoney(player, railroadOwner, railroadAmount);
			}
			return;
		case CORNER:
			if (space.equals(Space.GO_TO_JAIL)) {
				setSpace(player, Space.JAIL);
				playersInJail.add(player);
			}
			return;
		case OTHER:
			if (space.equals(Space.LUXURY_TAX)) {
				addMoney(player, -Space.LUXURY_TAX.getRent(0));
			} else if (space.equals(Space.INCOME_TAX)) {
				double incomeTaxAmount = Math.min(Space.INCOME_TAX.getRent(0),
						getEntireWealth(player) * Space.INCOME_TAX.getRent(1));
				addMoney(player, -incomeTaxAmount);
			}
		default:

			// Do nothing if mortgaged
			if (spaceToRecordMap.get(space).isMortgaged) {
				return;
			}

			MonopolyPlayer spaceOwner = spaceToRecordMap.get(space).getOwner();

			// If the player needs to pay rent
			if (spaceOwner != null && spaceOwner != player) {

				int colorGroupOwned = 0;
				int colorGroupTotal = 0;
				ColorGroup colorGroup = space.getColorGroup();

				for (Space s : colorGroup.getSpaces()) {
					colorGroupTotal++;

					if (spaceOwner == spaceToRecordMap.get(s).getOwner()) {
						colorGroupOwned++;
					}
				}
				int spaceImprovements = spaceToRecordMap.get(space)
						.getNumImprovements();
				double amountOwed = space.getRent(spaceImprovements);

				// If the owner has not made any improvements, but owns the
				// entire set, then the owner gets double rent
				if (spaceImprovements == 0
						&& colorGroupOwned == colorGroupTotal) {
					amountOwed *= 2.0;
				}

				transferMoney(player, spaceOwner, amountOwed);
			}
		}
	}

	/**
	 * Draws a community chest card. If the turn is completed, then returns
	 * true, otherwise return false (community chest cards always return true).
	 * 
	 * @param player
	 * @return
	 */
	private boolean drawCommunityChestCard(MonopolyPlayer player) {

		int cardNumber = random.nextInt(17);

		switch (cardNumber) {
		case 0:
			// Advance to Go (Collect $200)
			setSpace(player, Space.GO);
			addMoney(player, MONEY_PASS_GO);
			return true;
		case 1:
			// Bank error in your favor
			addMoney(player, 75);
			return true;
		case 2:
			// Doctor's fees – Pay $50
			addMoney(player, -50);
			return true;
		case 3:
			// Get out of jail free
			addMoney(player, 50); // Not keeping track of this card, just adding
									// 50 to account
			return true;
		case 4:
			// Go to jail
			setSpace(player, Space.JAIL);
			playersInJail.add(player);
			return true;
		case 5:
			// It is your birthday - collect $10 from each player
			for (MonopolyPlayer otherPlayer : players) {
				addMoney(otherPlayer, -10);
			}
			addMoney(player, players.size() * 10);
			return true;
		case 6:
			// Grand Opera Night – collect $50 from every player
			for (MonopolyPlayer otherPlayer : players) {
				addMoney(otherPlayer, -50);
			}
			addMoney(player, players.size() * 50);
			return true;
		case 7:
			// Income Tax refund – collect $20
			addMoney(player, 20);
			return true;
		case 8:
			// Life Insurance Matures – collect $100
			addMoney(player, 100);
			return true;
		case 9:
			// Pay Hospital Fees of $100
			addMoney(player, -100);
			return true;
		case 10:
			// Pay School Fees of $50
			addMoney(player, -50);
			return true;
		case 11:
			// Receive $25 Consultancy Fee
			addMoney(player, 25);
			return true;
		case 12:
			// You are assessed for street repairs – $40 per house, $115 per
			// hotel
			double totalOwed = 0;

			List<Space> properties = playerToRecordMap.get(player)
					.getProperties();
			for (Space property : properties) {
				int spaceImprovements = spaceToRecordMap.get(property)
						.getNumImprovements();

				if (spaceImprovements <= 4) {
					totalOwed += spaceImprovements * 40;
				} else {
					totalOwed += 115;
				}
			}

			addMoney(player, -totalOwed);
			return true;
		case 13:
			// You have won second prize in a beauty contest– collect $10
			addMoney(player, 10);
			return true;
		case 14:
			// You inherit $100
			addMoney(player, 100);
			return true;
		case 15:
			// From sale of stock you get $50
			addMoney(player, 50);
			return true;
		case 16:
			// Holiday Fund matures - Receive $100
			addMoney(player, 100);
			return true;
		}

		return false;
	}

	/**
	 * Draws a chance card. If the turn is completed, then returns true,
	 * otherwise return false (there are only a few chance cards that return
	 * false. These are the cards that move the player to a new space, which
	 * could require additional processing).
	 * 
	 * @param player
	 * @return
	 */
	private boolean drawChanceCard(MonopolyPlayer player) {
		int cardNumber = random.nextInt(17);

		switch (cardNumber) {
		case 0:
			// Advance to Go (Collect $200)
			setSpace(player, Space.GO);
			addMoney(player, MONEY_PASS_GO);
			return true;
		case 1:
			// Advance to Illinois Ave.
			Space curSpace = playerToRecordMap.get(player).getSpace();
			// If pass Go
			if (curSpace.ordinal() > Space.ILLINOIS_AVENUE.ordinal()) {
				addMoney(player, MONEY_PASS_GO);
			}
			setSpace(player, Space.ILLINOIS_AVENUE);
			return false;
		case 2:
			// Advance token to nearest Utility. If unowned, you may buy it from
			// the Bank. If owned, throw dice and pay owner a total ten times
			// the amount thrown.
			Space nextUtility = playerToRecordMap.get(player).getSpace();

			while (!nextUtility.getColorGroup().equals(ColorGroup.UTILITY)) {
				int nextSpace = (nextUtility.ordinal() + 1) % 40;
				nextUtility = Space.values()[nextSpace];
			}

			setSpace(player, nextUtility);

			if (spaceToRecordMap.get(nextUtility).isMortgaged) {
				return true;
			}

			MonopolyPlayer utilityOwner = spaceToRecordMap.get(nextUtility)
					.getOwner();

			if (utilityOwner != null && utilityOwner != player) {
				double amount = simpleRollDice() * 10;

				transferMoney(player, utilityOwner, amount);
			}
			return true;
		case 3:
		case 4:
			// Advance token to the nearest Railroad and pay owner twice the
			// rental to which he/she is otherwise entitled. If Railroad is
			// unowned, you may buy it from the Bank. (There are two of these.)

			Space nextRailroad = playerToRecordMap.get(player).getSpace();

			while (!nextRailroad.getColorGroup().equals(ColorGroup.RAILROAD)) {
				int nextSpace = (nextRailroad.ordinal() + 1) % 40;
				nextRailroad = Space.values()[nextSpace];
			}

			setSpace(player, nextRailroad);

			// If mortgaged, then we are done
			if (spaceToRecordMap.get(nextRailroad).isMortgaged) {
				return true;
			}

			MonopolyPlayer railroadOwner = spaceToRecordMap.get(nextRailroad)
					.getOwner();

			if (railroadOwner != null && railroadOwner != player) {

				int numRailroadsByOwner = 0;

				for (Space rr : ColorGroup.RAILROAD.getSpaces()) {
					if (railroadOwner == spaceToRecordMap.get(rr).getOwner()) {
						numRailroadsByOwner++;
					}
				}

				// Multiple the railroad rent by 2
				double amount = nextRailroad.getRent(numRailroadsByOwner) * 2;

				transferMoney(player, railroadOwner, amount);
			}
			return true;
		case 5:
			// Advance to St. Charles Place – if you pass Go, collect $200
			Space curSpace2 = playerToRecordMap.get(player).getSpace();
			// If pass Go
			if (curSpace2.ordinal() > Space.ST_CHARLES_PLACE.ordinal()) {
				addMoney(player, MONEY_PASS_GO);
			}
			setSpace(player, Space.ST_CHARLES_PLACE);
			return false;
		case 6:
			// Bank pays you dividend of $50
			addMoney(player, 50);
			return true;
		case 7:
			// Get out of jail free
			addMoney(player, 50); // Not keeping track of this card, just adding
			// 50 to account
			return true;
		case 8:
			// Go back 3 spaces
			Space curSpace3 = playerToRecordMap.get(player).getSpace();
			curSpace3 = Space.values()[curSpace3.ordinal() - 3];
			setSpace(player, curSpace3);
			return false;
		case 9:
			// Go directly to Jail
			setSpace(player, Space.JAIL);
			playersInJail.add(player);
			return true;
		case 10:
			// Make general repairs on all your property – for each house pay
			// $25 – for each hotel $100
			double totalOwed = 0;

			List<Space> properties = playerToRecordMap.get(player)
					.getProperties();
			for (Space property : properties) {
				int spaceImprovements = spaceToRecordMap.get(property)
						.getNumImprovements();

				if (spaceImprovements <= 4) {
					totalOwed += spaceImprovements * 25;
				} else {
					totalOwed += 100;
				}
			}

			addMoney(player, -totalOwed);
			return true;
		case 11:
			// Pay poor tax of $15
			addMoney(player, -15);
			return true;
		case 12:
			// Take a trip to Reading Railroad – if you pass Go collect $200
			Space curSpace4 = playerToRecordMap.get(player).getSpace();
			// If pass Go
			if (curSpace4.ordinal() > Space.READING_RAILROAD.ordinal()) {
				addMoney(player, MONEY_PASS_GO);
			}
			setSpace(player, Space.READING_RAILROAD);
			return false;
		case 13:
			// Take a walk on the Boardwalk – advance token to Boardwalk
			setSpace(player, Space.BOARDWALK);
			return false;
		case 14:
			// You have been elected chairman of the board – pay each player $50
			for (MonopolyPlayer otherPlayer : players) {
				addMoney(otherPlayer, 50);
			}
			addMoney(player, players.size() * -50);
			return true;
		case 15:
			// Your building loan matures – collect $150
			addMoney(player, 150);
			return true;
		case 16:
			// You have won a crossword competition - collect $100
			addMoney(player, 100);
			return true;
		}

		return false;
	}

	/**
	 * Moves the player to the specified space.
	 * 
	 * @param player
	 * @param newSpace
	 */
	private void setSpace(MonopolyPlayer player, Space newSpace) {
		playerToRecordMap.get(player).setSpace(newSpace);
	}

	/**
	 * Adds money to the player's account.
	 * 
	 * @param player
	 * @param amount
	 */
	private void addMoney(MonopolyPlayer player, double amount) {

		playerToRecordMap.get(player).addMoney(amount);

		// Keep track of the last player to get money in case the current player
		// goes bankrupt
		if (amount < 0) {
			// Last paid money to the bank (null)
			lastPlayerToReceiveMoney = null;
		}
	}

	/**
	 * Move money from one player to another.
	 * 
	 * @param fromPlayer
	 * @param toPlayer
	 * @param amount
	 */
	private void transferMoney(MonopolyPlayer fromPlayer,
			MonopolyPlayer toPlayer, double amount) {
		addMoney(fromPlayer, -amount);
		addMoney(toPlayer, amount);

		// Keep track of the last player to get money in case the current player
		// goes bankrupt
		lastPlayerToReceiveMoney = toPlayer;
	}

	/**
	 * Roll dice when deciding values for community chest or chance cards.
	 * 
	 * @return
	 */
	private int simpleRollDice() {
		return random.nextInt(6) + random.nextInt(6) + 2;
	}

	/**
	 * There is record-keeping to prevent a player from performing actions under
	 * the disguise of another player. For example, I want to prevent a player
	 * from selling the houses of another player in order to improve there
	 * changes of winning. So, this method determines if several of the other
	 * methods will be allowed or not.
	 * 
	 * In short, it just makes sure that it is the player's turn and that there
	 * is no trading in progress (since during trading other players' methods
	 * are being called and this presents the opportunity for the type of method
	 * calls mentioned above).
	 * 
	 * @param player
	 * 
	 * @return
	 */
	private boolean operationAllowed(MonopolyPlayer player) {

		// Make sure it is the player's turn and that there is no trading in
		// progress
		return player != null && currentPlayer == player && !tradeInProgress;
	}

	//
	//
	//
	//
	// Package visible methods used for displaying info about the game.
	//
	//
	//
	//

	SpaceRecord getSpaceRecord(Space space) {
		return spaceToRecordMap.get(space);
	}

	//
	//
	//
	//
	// Inner class used for storing game info.
	//
	//
	//
	//

	/**
	 * An inner class to keep track of all of the information regarding a
	 * MonopolyPlayer. Also, takes care of rolling the dice for the player.
	 * 
	 * @author randy
	 *
	 */
	private class MonopolyPlayerRecord implements Steppable {

		private static final long serialVersionUID = 1L;

		private MonopolyPlayer player;

		private boolean playerBankrupt;

		private double money;

		private Space curSpace;

		private List<Space> properties;

		private boolean rolledDoubles = false;

		private int rolledDoublesCount = 0;

		private int attemptsAtGettingOutOfJail = 0;

		private MonopolyPlayerRecord(MonopolyPlayer player) {
			this.player = player;
			this.money = MONEY_START;
			this.curSpace = Space.GO;
			this.properties = new ArrayList<Space>();
			this.playerBankrupt = false;
		}

		@Override
		public void step(SimState state) {

			// Do nothing if bankrupt
			if (playerBankrupt) {
				return;
			}

			do {
				// Since starting a new turn
				currentPlayer = player;
				lastPlayerToReceiveMoney = null;
				tradeProposalsThisTurn = 0;

				// Roll dice and determine the next space index
				int nextLocation = curSpace.ordinal() + rollDice();

				// If the player is in jail
				if (playersInJail.contains(player)) {
					if (rolledDoubles) {
						playersInJail.remove(player);
					} else {
						// Keep track of number of attempts
						attemptsAtGettingOutOfJail++;

						// Just stay in jail
						nextLocation = curSpace.ordinal();

						// If you tried to get out of jail three times, then you
						// have to pay to get out
						if (attemptsAtGettingOutOfJail >= 3) {
							playersInJail.remove(player);
							attemptsAtGettingOutOfJail = 0;
							money -= MONEY_GET_OUT_OF_JAIL;
						}
					}
				} else {
					attemptsAtGettingOutOfJail = 0;
				}

				// If doubles haven't been rolled 3 times, then it is okay to
				// move the player
				if (rolledDoublesCount < 3) {
					// Check if passed GO
					if (nextLocation >= 40) {
						nextLocation = nextLocation % 40;
						money += MONEY_PASS_GO;
					}

					// Set the next space
					setSpace(Space.values()[nextLocation]);

					// Do any account updates required for new space
					handleNextSpace(player);

					// Have the player perform some actions
					player.step(state);
				}
				// Doubles rolled three times, so put player in jail
				else {
					// Put the player in jail and end turn
					playersInJail.add(player);
					setSpace(Space.JAIL);
				}

				// After each roll, check to see if the player went bankrupt
				if (checkIfInBankruptcy()) {

					// Release any properties that the player owns
					releaseProperties();

					playerBankrupt = true;
					playerLocationGrid.setObjectLocation(player,
							getNumberOfBankruptPlayers() + 3, 8);

					// Remove player from active players
					players.remove(player);
					playersInBankruptcy.add(player);
				}

			} while (rolledDoubles && rolledDoublesCount < 3 && !playerBankrupt);

			rolledDoubles = false;
			rolledDoublesCount = 0;
		}

		private boolean isBankrupt() {
			return playerBankrupt;
		}

		private boolean checkIfInBankruptcy() {
			boolean moreImprovementsToSell = true;

			while (money < 0 && moreImprovementsToSell) {

				moreImprovementsToSell = false;

				// Try to mortgage some property
				// Sell any improvements (houses or hotels)
				for (Space property : properties) {
					SpaceRecord propertyRecord = spaceToRecordMap.get(property);

					if (propertyRecord.getNumImprovements() > 0) {
						propertyRecord.sellImprovement(HOUSE_IMPROVEMENT);

						if (money >= 0) {
							return false;
						}

						if (propertyRecord.getNumImprovements() > 0) {
							moreImprovementsToSell = true;
						}
					}
				}
			}

			if (money < 0) {

				// Mortgage any properties
				for (Space property : properties) {
					SpaceRecord propertyRecord = spaceToRecordMap.get(property);

					if (!propertyRecord.isMortgaged()) {
						propertyRecord.mortgageProperty();

						if (money >= 0) {
							return false;
						}
					}
				}
			}

			return money < 0;
		}

		private void releaseProperties() {
			for (Space property : properties) {
				SpaceRecord propertyRecord = spaceToRecordMap.get(property);

				propertyRecord.owner = lastPlayerToReceiveMoney;

				// Give the property to the player that bankrupted this player
				if (lastPlayerToReceiveMoney != null) {
					playerToRecordMap.get(lastPlayerToReceiveMoney)
							.addProperty(property);
				}

				// If the property goes back to the bank, then remove the
				// mortgage
				if (lastPlayerToReceiveMoney == null) {
					propertyRecord.isMortgaged = false;
				}
			}

			// If the bankrupt player has less than zero money, then that means
			// the last player to receive money received too much money
			if (money < 0) {
				// If the bankrupt player was not paying the bank, then update
				// the account balance of the last player to receive money
				if (lastPlayerToReceiveMoney != null) {
					Monopoly.this.addMoney(lastPlayerToReceiveMoney, money);
				}
			}

			// Remove properties from bankrupt player
			properties.clear();
		}

		private int rollDice() {
			int dice1 = random.nextInt(6) + 1;
			int dice2 = random.nextInt(6) + 1;

			rolledDoubles = dice1 == dice2;

			if (rolledDoubles) {
				rolledDoublesCount++;
			}

			return dice1 + dice2;
		}

		private Space getSpace() {
			return curSpace;
		}

		private double getMoney() {
			return money;
		}

		private double getEntireWealth() {
			double entireWealth = money;

			for (Space property : properties) {
				SpaceRecord propertyRecord = spaceToRecordMap.get(property);

				entireWealth += propertyRecord.isMortgaged() ? property
						.getMortgageValue() : property.getPrice();

				entireWealth += propertyRecord.getNumImprovements()
						* property.getBuildingCost();
			}

			return entireWealth;
		}

		private List<Space> getProperties() {
			return Collections.unmodifiableList(properties);
		}

		private void purchaseProperty(Space space) {
			money -= space.getPrice();
			properties.add(space);

			SpaceRecord spaceRecord = spaceToRecordMap.get(space);
			spaceRecord.owner = player;
		}

		private void addProperty(Space space) {
			properties.add(space);

			SpaceRecord spaceRecord = spaceToRecordMap.get(space);
			spaceRecord.owner = player;
		}

		private void removeProperty(Space space) {
			properties.remove(space);

			SpaceRecord spaceRecord = spaceToRecordMap.get(space);
			spaceRecord.owner = null;
		}

		private void addMoney(double amount) {
			money += amount;
		}

		private void setSpace(Space space) {
			curSpace = space;

			playerLocationGrid.setObjectLocation(player, curSpace.getXLoc(),
					curSpace.getYLoc());
		}
	}

	public class SpaceRecord {

		private Space space;

		private MonopolyPlayer owner;

		private int numImprovements;

		private boolean isMortgaged;

		private SpaceRecord(Space space) {
			this.space = space;
			this.owner = null;
			this.numImprovements = 0;
			this.isMortgaged = false;
		}

		public Space getSpace() {
			return space;
		}

		public MonopolyPlayer getOwner() {
			return owner;
		}

		public int getNumImprovements() {
			return numImprovements;
		}

		private boolean isMoreImprovementsPossible(
				int amountOfAdditionalImprovement) {
			return numImprovements + amountOfAdditionalImprovement < space
					.getPossibleNumImprovements();
		}

		private boolean makeImprovement(int amountOfImprovement) {

			if (owner == null) {
				return false;
			}

			MonopolyPlayerRecord ownerRecord = playerToRecordMap.get(owner);

			double improvementCost = amountOfImprovement
					* space.getBuildingCost();

			ownerRecord.addMoney(-improvementCost);
			numImprovements += amountOfImprovement;

			return true;
		}

		private boolean sellImprovement(int amountToSell) {

			if (owner == null) {
				return false;
			}

			MonopolyPlayerRecord ownerRecord = playerToRecordMap.get(owner);

			double improvementProfit = amountToSell * space.getBuildingCost()
					/ 2.0;

			ownerRecord.addMoney(improvementProfit);
			numImprovements -= amountToSell;

			return true;
		}

		public boolean isMortgaged() {
			return isMortgaged;
		}

		private boolean mortgageProperty() {
			if (owner == null || isMortgaged) {
				return false;
			}

			MonopolyPlayerRecord ownerRecord = playerToRecordMap.get(owner);

			ownerRecord.addMoney(space.getMortgageValue());
			isMortgaged = true;

			return true;
		}

		private boolean unmortgageProperty() {
			if (owner == null || !isMortgaged) {
				return false;
			}

			MonopolyPlayerRecord ownerRecord = playerToRecordMap.get(owner);

			// There is 10% interest on mortgage
			double mortgageCost = space.getMortgageValue() * 1.1;

			// Do not have the money for the mortgage
			if (ownerRecord.getMoney() < mortgageCost) {
				return false;
			}

			ownerRecord.addMoney(-mortgageCost);
			isMortgaged = false;

			return true;
		}

		public String toString() {
			return space.toString();
		}
	}

	//
	//
	//
	// Methods for creating players from a file.
	//
	//
	//

	private static final File MONOPOLY_PLAYER_FILE = new File(Monopoly.class
			.getResource("monopoly_players.txt").getFile());

	private static List<MonopolyPlayer> createPlayersFromFile(File playerFile) {
		BufferedReader in = null;
		List<MonopolyPlayer> retval = new ArrayList<MonopolyPlayer>();
		try {
			// Create the reader for the filename
			in = new BufferedReader(new FileReader(playerFile));

			// While there is still another line, then parse it
			String nextLine = null;
			while ((nextLine = in.readLine()) != null) {

				// If the line is not commented out
				if (!nextLine.startsWith("//") && !nextLine.startsWith("#")) {

					String className = nextLine;

					Class<?> c = null;

					try {
						// See if the class can be found in this package
						c = Class.forName(Monopoly.class.getPackage().getName()
								+ "." + className);
					} catch (ClassNotFoundException e) {
						// If class not in this package, then just try the
						// class name by itself
						c = Class.forName(className);
					}

					MonopolyPlayer monopolyPlayer = (MonopolyPlayer) c
							.newInstance();
					retval.add(monopolyPlayer);
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

	public static void main(String[] args) {
		doLoop(Monopoly.class, args);
		System.exit(0);
	}
}
