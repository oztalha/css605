package edu.gmu.toz.competition.monopoly;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum ColorGroup {

	PURPLE(new Color(76, 0, 153)), //
	LIGHT_BLUE(Color.CYAN), //
	VIOLET(Color.MAGENTA), //
	ORANGE(Color.ORANGE), //
	RED(Color.RED), //
	YELLOW(Color.YELLOW), //
	GREEN(Color.GREEN), //
	BLUE(Color.BLUE), //
	UTILITY(Color.LIGHT_GRAY), //
	RAILROAD(Color.DARK_GRAY), //
	COMMUNITY_CHEST(Color.PINK), //
	CHANCE(Color.PINK), //
	CORNER(Color.WHITE), //
	OTHER(Color.WHITE); // luxury tax and income tax

	private final Color javaColor;

	private final List<Space> spaceList = new ArrayList<Space>();

	private ColorGroup(Color javaColor) {
		this.javaColor = javaColor;
	}

	public Color getColor() {
		return javaColor;
	}

	void addSpace(Space s) {
		spaceList.add(s);
	}

	public List<Space> getSpaces() {
		return Collections.unmodifiableList(spaceList);
	}

}
