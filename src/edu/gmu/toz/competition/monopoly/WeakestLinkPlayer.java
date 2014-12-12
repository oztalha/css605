package edu.gmu.toz.competition.monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sim.engine.SimState;
/**
 * My MonopolyPlayer will try to find "the weakest link",
 * i.e. the person(s) who are willing to sell his lands with minimum (proposedValue / actualValue) ratio.
 * Based on my personal experience in this game, it is more important to evaluate the willingness of other players
 * to cooperate or sell their lands rather than insisting on trying to buy a particular land of a particular color.
 * Also, the first person who manages to have the right to build houses on a land is likely to win
 * unless the other players cooperate in the next few turns. Therefore, my player will propose trades as much as it can
 * to be able to be the first person collecting all the lands of the same color. Finally, I will be extremely unwilling
 * to trade a land that will lead the opponent to complete the set of the same colors
 * unless I am in the very same situation (i.e. he offers me such a land).
 * @author toz
 *
 */
public class WeakestLinkPlayer extends AbstractMonopolyPlayer {
	// this comparator is used to sort the opponents by entire wealth
	class WealthComparator implements Comparator<MonopolyPlayer> {
	    @Override
	    public int compare(MonopolyPlayer a, MonopolyPlayer b) {
	        return monopoly.getEntireWealth(a) < monopoly.getEntireWealth(b) ? -1 : monopoly.getEntireWealth(a) == monopoly.getEntireWealth(b) ? 0 : 1;
	    }
	}
	private static final long serialVersionUID = 1L;
	
	@Override
	public void step(SimState state) {
		try{
		Monopoly monopoly = (Monopoly) state;
		Space curSpace = monopoly.getSpace(this);
		
		// always buy if you can
		if(monopoly.isForSale(curSpace)){
			if(getMoney()>curSpace.getPrice())
				monopoly.purchaseSpace(this);
		}
		
		// always build if you can
		List<Space> properties = monopoly.getProperties(this);
		for (Space property : properties) {
			monopoly.unmortgageProperty(this, property);
			boolean attempt;
			do //try to build as much as you can
				attempt = monopoly.buildImprovement(this, property,Monopoly.HOUSE_IMPROVEMENT);
			while(attempt);
		}

		// now it's time to prepare proposals :-)
		
		// first, let's try to meet our immediate needs,
		// i.e. try hard to purchase needOnlyOneForSet if there is such any 
		proposeTrade();
		// if I don't have much money left, stop for this step
		if(getMoney()<500)
			return;
		// sort the players by their wealth
		List<MonopolyPlayer> players = new ArrayList<MonopolyPlayer>(monopoly.getPlayers());
		Collections.sort(players, new WealthComparator());
		// get a list of every space that has been purchased already
		// and try to buy them by offering less then their actual value to me
		// start proposing from the weakest link, i.e. who has the least wealth
		for(MonopolyPlayer friend : players){
			if(friend.equals(this))
				continue;
			
			for(Space space : monopoly.getProperties(friend)){
				boolean mylastOffer = false;
				//I first offer its actual value + $1.
				//if that offer fails than I increment by 20% at every step until
				// I offer half of my money or I I have only $1000 left after the offer or I offer 5.1 times more than its price
				for(double myOffer = space.getPrice()+1;!mylastOffer && myOffer<getMoney()*0.50 && getMoney()-myOffer>1000 && myOffer<space.getPrice()*5.1;myOffer*=1.2)
					mylastOffer = monopoly.proposeTrade(this, myOffer, null, 0, space);
			}
		}
		}catch (Exception e){ return; }
	}
		

	private void proposeTrade() {
		// Get the properties of this player
		List<Space> props = monopoly.getProperties(this);

		// Iterate through the player's properties
		for (Space s : props) {
			Space neededSpace = needOnlyOneForSet(s);
			if (neededSpace != null) {
				// Propose trade hard if it's the only needed space !
				boolean mylastOffer = false;
				//I first offer its actual value + $1.
				//if that offer fails than I increment by 20% at every step until
				// I offer .75 of my money or I have only $360 left after the offer
				for(double myOffer = neededSpace.getPrice()+1;!mylastOffer && myOffer<getMoney()*0.75 && getMoney()-myOffer>360;myOffer*=1.2)
					mylastOffer = monopoly.proposeTrade(this, myOffer, null, 0, neededSpace);
			}
		}
	}
	// Returns the space that I need
	private Space needOnlyOneForSet(Space space) {
		List<Space> curProperties = monopoly.getProperties(this);
		ColorGroup scg = space.getColorGroup();
		List<Space> missingOnesForSet = new ArrayList<Space>();
		for (Space s : Space.values())
			//if same color group and not my property then add it to missings
			if (s.getColorGroup().equals(scg) && !curProperties.contains(s))
					missingOnesForSet.add(s);
		//if there is one and only one missing space then return it
		if (missingOnesForSet.size() == 1)
			return missingOnesForSet.get(0);
		return null;
	}
	

	@Override
	public boolean considerTrade(TradeProposal tradeProposal) {
		// if inCash is positive then it is a different case...
		double inCash = tradeProposal.getOfferedMoney() - tradeProposal.getRequestedMoney();
		 List<Space> myProp = getProperties();
		// if I don't own the requested property return false
		for(Space s : tradeProposal.getRequestedProperties())
			if (!myProp.contains(s))
				return false;
		// my default trade preference is false unless opponent convinces me
		boolean offeredPropMakeSense = false;
		boolean myneededSpace =  false;
		//if opponent is asking for money
		if (inCash<0){
			double askingFromMe = -inCash;
			//then he should be offering some property
			for (Space s : tradeProposal.getOfferedProperties()){
				Space neededSpace = needOnlyOneForSet(s);
				// -inCash means how much he asks from me
				if (neededSpace != null && askingFromMe<getMoney()*0.75 && getMoney()-askingFromMe>360){
					offeredPropMakeSense = true; // the same rules as in my neededOne of a set proposal above 
					myneededSpace = true; //offering the one I need most !
					break;
				}else if(askingFromMe<getMoney()*0.50 && getMoney()-askingFromMe>1000 && askingFromMe<s.getPrice()*5.1)
					offeredPropMakeSense = true;// the same rules as in my weakest link proposal loop above
			}
		}else{ //if giving money then it means asking for my prop
			double hisOffer = inCash;
			// go over requested prop
			for(Space sr : tradeProposal.getRequestedProperties()){
				Space needed = needOnlyOneForSet(sr);  
				if (needed != null)//if I am about to complete that set
					if(myneededSpace == false) //unless I'm completing another set
						return false; //reject
					else{
						offeredPropMakeSense = true; //accept
						break;
					}
				//if he is asking for a color that I'm not about to complete a set
				else if(hisOffer>=sr.getPrice()*10 || (hisOffer>getMoney() && hisOffer>sr.getPrice()+3)){
					// then accept offer if he is willing to pay much enough !
					offeredPropMakeSense = true; //i.e. 10 times more or (more than my cash amount & more than its actual price) 
				}
			}
		}
		//if offeredPropMakeSense
		if(offeredPropMakeSense)
			return true;

		return false;
	}

	@Override
	public boolean considerCounter(TradeProposal tradeCounterProposal) {
		return false;
	}
	
	@Override
	public String toString(){
		return "WeakestLinkPlayer";
	}

}
