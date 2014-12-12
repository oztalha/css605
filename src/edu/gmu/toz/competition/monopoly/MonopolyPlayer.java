/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package edu.gmu.toz.competition.monopoly;

import sim.engine.*;

public interface MonopolyPlayer extends Steppable {

	/**
	 * In this method, you should purchase property, build houses, and propose
	 * trades with others.
	 * 
	 */
	public void step(final SimState state);

	/**
	 * If the player returns false, then you reject the proposal.
	 * 
	 * If the player returns true, without changing the proposal, then the
	 * proposal is accepted.
	 * 
	 * If the player changes the proposal and return true, then it is a counter
	 * proposal.
	 * 
	 * @param tradeProposal
	 * @return
	 */
	public boolean considerTrade(TradeProposal tradeProposal);

	/**
	 * If you propose a trade, then another player can provide you with a
	 * counter proposal. You must either return true and accept or reject by
	 * returning false, but no changes to the proposal is allowed.
	 * 
	 * @param tradeCounterProposal
	 * @return
	 */
	public boolean considerCounter(TradeProposal tradeCounterProposal);

}
