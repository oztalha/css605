package edu.gmu.toz.competition.monopoly;

import java.awt.Color;

import sim.util.Double2D;

public enum Space {

	GO(ColorGroup.CORNER, Double.MAX_VALUE, 0, Double.MAX_VALUE, new double[0],
			10, 10), //
	MEDITERRANEAN_AVENUE(ColorGroup.PURPLE, 60, 30, 50, new double[] { 2, 10,
			30, 90, 160, 250 }, 9, 10), //
	COMMUNITY_CHEST1(ColorGroup.COMMUNITY_CHEST, Double.MAX_VALUE, 0,
			Double.MAX_VALUE, new double[0], 8, 10), //
	BALTIC_AVENUE(ColorGroup.PURPLE, 60, 30, 50, new double[] { 4, 20, 60, 180,
			320, 450 }, 7, 10), //
	INCOME_TAX(ColorGroup.OTHER, Double.MAX_VALUE, 0, Double.MAX_VALUE,
			new double[] { 200, .10 }, 6, 10), //
	READING_RAILROAD(ColorGroup.RAILROAD, 200, 100, Double.MAX_VALUE,
			new double[] { 0, 25, 50, 100, 200 }, 5, 10), //
	ORIENTAL_AVENUE(ColorGroup.LIGHT_BLUE, 100, 50, 50, new double[] { 12, 30,
			90, 270, 400, 550 }, 4, 10), //
	CHANCE1(ColorGroup.CHANCE, Double.MAX_VALUE, 0, Double.MAX_VALUE,
			new double[] {}, 3, 10), //
	VERMONT_AVENUE(ColorGroup.LIGHT_BLUE, 100, 50, 50, new double[] { 6, 30,
			90, 270, 400, 550 }, 2, 10), //
	CONNECTICUT_AVENUE(ColorGroup.LIGHT_BLUE, 120, 60, 50, new double[] { 8,
			40, 100, 300, 450, 600 }, 1, 10), //
	JAIL(ColorGroup.CORNER, Double.MAX_VALUE, 0, Double.MAX_VALUE,
			new double[0], 0, 10), //
	ST_CHARLES_PLACE(ColorGroup.VIOLET, 140, 70, 100, new double[] { 10, 50,
			150, 450, 625, 750 }, 0, 9), //
	ELECTRIC_COMPANY(ColorGroup.UTILITY, 150, 75, Double.MAX_VALUE,
			new double[] { 0, 4, 10 }, 0, 8), //
	STATES_AVENUE(ColorGroup.VIOLET, 140, 70, 100, new double[] { 10, 50, 150,
			450, 625, 750 }, 0, 7), //
	VIRGINIA_AVENUE(ColorGroup.VIOLET, 160, 80, 100, new double[] { 12, 60,
			180, 500, 700, 900 }, 0, 6), //
	PENNSYLVANIA_RAILROAD(ColorGroup.RAILROAD, 200, 100, Double.MAX_VALUE,
			new double[] { 0, 25, 50, 100, 200 }, 0, 5), //
	ST_JAMES_PLACE(ColorGroup.ORANGE, 180, 90, 100, new double[] { 14, 70, 200,
			550, 750, 950 }, 0, 4), //
	COMMUNITY_CHEST2(ColorGroup.COMMUNITY_CHEST, Double.MAX_VALUE, 0,
			Double.MAX_VALUE, new double[0], 0, 3), //
	TENNESSEE_AVENUE(ColorGroup.ORANGE, 180, 90, 100, new double[] { 14, 70,
			200, 550, 750, 950 }, 0, 2), //
	NEW_YORK_AVENUE(ColorGroup.ORANGE, 200, 100, 100, new double[] { 16, 80,
			220, 600, 800, 1000 }, 0, 1), //
	FREE_PARKING(ColorGroup.CORNER, Double.MAX_VALUE, 0, Double.MAX_VALUE,
			new double[0], 0, 0), //
	KENTUCKY_AVENUE(ColorGroup.RED, 220, 110, 150, new double[] { 18, 90, 250,
			700, 875, 1050 }, 1, 0), //
	CHANCE2(ColorGroup.CHANCE, Double.MAX_VALUE, 0, Double.MAX_VALUE,
			new double[] {}, 2, 0), //
	INDIANA_AVENUE(ColorGroup.RED, 220, 110, 150, new double[] { 18, 90, 250,
			700, 875, 1050 }, 3, 0), //
	ILLINOIS_AVENUE(ColorGroup.RED, 240, 120, 150, new double[] { 20, 100, 300,
			750, 925, 1100 }, 4, 0), //
	B_AND_O_RAILROAD(ColorGroup.RAILROAD, 200, 100, Double.MAX_VALUE,
			new double[] { 0, 25, 50, 100, 200 }, 5, 0), //
	ATLANTIC_AVENUE(ColorGroup.YELLOW, 260, 130, 150, new double[] { 22, 110,
			330, 800, 975, 1150 }, 6, 0), //
	VENTNOR_AVENUE(ColorGroup.YELLOW, 260, 130, 150, new double[] { 22, 110,
			330, 800, 975, 1150 }, 7, 0), //
	WATER_WORKS(ColorGroup.UTILITY, 150, 75, Double.MAX_VALUE, new double[] {
			0, 4, 10 }, 8, 0), //
	MARVIN_GARDENS(ColorGroup.YELLOW, 280, 140, 150, new double[] { 24, 120,
			360, 850, 1025, 1200 }, 9, 0), //
	GO_TO_JAIL(ColorGroup.CORNER, Double.MAX_VALUE, 0, Double.MAX_VALUE,
			new double[0], 10, 0), //
	PACIFIC_AVENUE(ColorGroup.GREEN, 300, 150, 200, new double[] { 26, 130,
			390, 900, 1100, 1275 }, 10, 1), //
	NORTH_CAROLINE_AVENUE(ColorGroup.GREEN, 300, 150, 200, new double[] { 26,
			130, 390, 900, 1100, 1275 }, 10, 2), //
	COMMUNITY_CHEST3(ColorGroup.COMMUNITY_CHEST, Double.MAX_VALUE, 0,
			Double.MAX_VALUE, new double[0], 10, 3), //
	PENNSYLVANIA_AVENUE(ColorGroup.GREEN, 320, 160, 200, new double[] { 28,
			150, 450, 1000, 1200, 1400 }, 10, 4), //
	SHORT_LINE(ColorGroup.RAILROAD, 200, 100, Double.MAX_VALUE, new double[] {
			0, 25, 50, 100, 200 }, 10, 5), //
	CHANCE3(ColorGroup.CHANCE, Double.MAX_VALUE, 0, Double.MAX_VALUE,
			new double[] {}, 10, 6), //
	PARK_PLACE(ColorGroup.BLUE, 350, 175, 200, new double[] { 35, 175, 500,
			1100, 1300, 1500 }, 10, 7), //
	LUXURY_TAX(ColorGroup.OTHER, Double.MAX_VALUE, 0, Double.MAX_VALUE,
			new double[] { 75 }, 10, 8), //
	BOARDWALK(ColorGroup.BLUE, 400, 200, 200, new double[] { 50, 200, 600,
			1400, 1700, 2000 }, 10, 9);

	public static Space[] RAILROADS = new Space[] { READING_RAILROAD,
			PENNSYLVANIA_RAILROAD, B_AND_O_RAILROAD, SHORT_LINE };

	public static Space[] UTILITIES = new Space[] { ELECTRIC_COMPANY,
			WATER_WORKS };

	private ColorGroup colorGroup;

	private double price, mortgageValue, buildingCost;

	private double[] rents;

	private Double2D loc;

	private int xLoc, yLoc;

	private Space(ColorGroup colorGroup, double price, double mortgageValue,
			double buildingCost, double[] rents, int xLoc, int yLoc) {
		this.colorGroup = colorGroup;
		this.price = price;
		this.mortgageValue = mortgageValue;
		this.buildingCost = buildingCost;
		this.rents = rents;
		this.loc = new Double2D(xLoc, yLoc);
		this.xLoc = xLoc;
		this.yLoc = yLoc;

		colorGroup.addSpace(this);
	}

	public ColorGroup getColorGroup() {
		return colorGroup;
	}

	public double getPrice() {
		return price;
	}

	public double getMortgageValue() {
		return mortgageValue;
	}

	public double getBuildingCost() {
		return buildingCost;
	}

	public double getRent(int numImprovements) {
		return rents[numImprovements];
	}

	public int getPossibleNumImprovements() {

		switch (colorGroup) {
		case RAILROAD:
			return 0;
		case CORNER:
			return 0;
		case OTHER:
			return 0;
		case UTILITY:
			return 0;
		case CHANCE:
			return 0;
		case COMMUNITY_CHEST:
			return 0;
		default:
			return rents.length;
		}
	}

	public Color getColor() {
		return colorGroup.getColor();
	}

	public int getXLoc() {
		return xLoc;
	}

	public int getYLoc() {
		return yLoc;
	}

	public Double2D getLoc() {
		return loc;
	}
}
