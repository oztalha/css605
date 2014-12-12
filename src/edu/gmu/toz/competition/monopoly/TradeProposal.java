package edu.gmu.toz.competition.monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TradeProposal {

	private double offeredMoney;

	private List<Space> offeredProperties;

	private double requestedMoney;

	private List<Space> requestedProperites;

	private boolean tradeAltered;

	public TradeProposal(double offeredMoney, List<Space> offeredProperties,
			double requestMoney, List<Space> requestedProperties) {
		this.offeredMoney = Math.max(offeredMoney, 0);

		this.offeredProperties = new ArrayList<Space>();
		if (offeredProperties != null) {
			this.offeredProperties.addAll(offeredProperties);
		}

		this.requestedMoney = Math.max(requestMoney, 0);

		this.requestedProperites = new ArrayList<Space>();
		if (requestedProperties != null) {
			this.requestedProperites.addAll(requestedProperties);
		}

		this.tradeAltered = false;
	}

	public TradeProposal(TradeProposal otherProposal) {
		this(otherProposal.offeredMoney, otherProposal.offeredProperties,
				otherProposal.requestedMoney, otherProposal.requestedProperites);
	}

	public double getOfferedMoney() {
		return offeredMoney;
	}

	public void setOfferedMoney(double money) {
		offeredMoney = Math.max(money, 0);
		tradeAltered = true;
	}

	public List<Space> getOfferedProperties() {
		return Collections.unmodifiableList(offeredProperties);
	}

	public void setOfferedProperties(Space property) {
		offeredProperties.clear();
		offeredProperties.add(property);
		tradeAltered = true;
	}

	public void setOfferedProperties(List<Space> properties) {
		offeredProperties.clear();
		offeredProperties.addAll(properties);
		tradeAltered = true;
	}

	public double getRequestedMoney() {
		return requestedMoney;
	}

	public void setRequestedMoney(double money) {
		requestedMoney = Math.max(money, 0);
		tradeAltered = true;
	}

	public List<Space> getRequestedProperties() {
		return Collections.unmodifiableList(requestedProperites);
	}

	public void setRequestedProperties(Space property) {
		requestedProperites.clear();
		requestedProperites.add(property);
		tradeAltered = true;
	}

	public void setRequestedProperties(List<Space> properties) {
		requestedProperites.clear();
		requestedProperites.addAll(properties);
		tradeAltered = true;
	}

	public boolean isTradedAltered() {
		return tradeAltered;
	}

}
