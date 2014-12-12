package edu.gmu.toz.competition.monopoly;

import java.util.InputMismatchException;
import java.util.Scanner;

import sim.engine.SimState;

/**
 * Class that allows someone to play against their MonopolyPlayer agent.
 * 
 * @author randy
 *
 */
public class HumanMonopolyPlayer extends AbstractMonopolyPlayer {

	private static final long serialVersionUID = 1L;

	private static int instanceNum = 1;

	private int playerNum = instanceNum++;

	private Scanner scanner = new Scanner(System.in);

	// Print warning message at beginning
	static {
		System.err
				.println("Take note that the display does not get updated until all players have had a turn, \nso when using the HumanMonopolyPlayer your current space will not be what is displayed.\n");

		System.err
				.println("Commands are: \n return -> finish turn \n a -> account balance \n l -> list properties \n p -> purchase property\n s -> list space indices\n b -> build house\n d -> sell house\n m -> mortgage property\n u -> unmortgage property\n t -> propose trade\n");
	}

	@Override
	public void step(SimState state) {
		Monopoly monopoly = (Monopoly) state;

		System.err.println("It is " + toString() + "'s turn...");
		System.err.println("Landed on: " + getSpace().ordinal() + " -> "
				+ getSpace());

		String next = scanner.nextLine();
		int nextInt;

		while (!next.equals("")) {
			try {
				if (next.startsWith("a")) {
					System.err.println("Money: " + getMoney());
				} else if (next.startsWith("l")) {
					System.err.println("Properties:");
					for (Space s : getProperties()) {
						System.err.print(s.ordinal() + " -> " + s + ", ");
					}
					System.err.println();
				} else if (next.startsWith("p")) {
					System.err.println("Successful purchase: "
							+ monopoly.purchaseSpace(this));
				} else if (next.startsWith("s")) {
					System.err.println("Space indices are:");
					for (int i = 0; i < Space.values().length; i++) {
						System.err.println("" + i + " -> " + Space.values()[i]);
					}
				} else if (next.startsWith("b")) {
					System.err.println("Build house where?:");
					nextInt = scanner.nextInt();
					next = scanner.nextLine();
					System.err.println("Successful build house: "
							+ monopoly.buildImprovement(this,
									Space.values()[nextInt],
									Monopoly.HOUSE_IMPROVEMENT));
				} else if (next.startsWith("d")) {
					System.err.println("Sell house where?:");
					nextInt = scanner.nextInt();
					next = scanner.nextLine();
					System.err.println("Successful sell house: "
							+ monopoly.sellImprovement(this,
									Space.values()[nextInt],
									Monopoly.HOUSE_IMPROVEMENT));
				} else if (next.startsWith("m")) {
					System.err.println("Mortgage where?:");
					nextInt = scanner.nextInt();
					next = scanner.nextLine();
					System.err.println("Successful mortgage: "
							+ monopoly.mortgageProperty(this,
									Space.values()[nextInt]));
				} else if (next.startsWith("u")) {
					System.err.println("Remove mortgage where?:");
					nextInt = scanner.nextInt();
					next = scanner.nextLine();
					System.err.println("Successful unmortgage: "
							+ monopoly.unmortgageProperty(this,
									Space.values()[nextInt]));
				} else if (next.startsWith("t")) {
					System.err.println("Request space (-1 for none)?:");
					int requestSpace = scanner.nextInt();
					System.err.println("Request money?:");
					int requestMoney = scanner.nextInt();
					System.err.println("Offer space (-1 for none)?:");
					int offerSpace = scanner.nextInt();
					System.err.println("Offer money?:");
					int offerMoney = scanner.nextInt();
					next = scanner.nextLine();

					System.err.println("Successful trade: "
							+ monopoly.proposeTrade(this, offerMoney,
									offerSpace == -1 ? null
											: Space.values()[offerSpace],
									requestMoney, requestSpace == -1 ? null
											: Space.values()[requestSpace]));
				}
			} catch (InputMismatchException e) {
				// Do nothing but just finish the player's turn
			}
			next = scanner.nextLine();
		}
	}

	@Override
	public boolean considerTrade(TradeProposal tradeProposal) {

		System.err.println(toString() + ": Someone proposed a trade?:");

		System.err.println("Offered money: " + tradeProposal.getOfferedMoney());
		System.err.println("Offered properties: "
				+ tradeProposal.getOfferedProperties());
		System.err.println("Requested money: "
				+ tradeProposal.getRequestedMoney());
		System.err.println("Requested properties: "
				+ tradeProposal.getRequestedProperties());

		System.err.println("Do you accept?");

		String acceptTrade = scanner.nextLine();

		return acceptTrade.startsWith("y");
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
		return "Human Player " + playerNum;
	}
}
