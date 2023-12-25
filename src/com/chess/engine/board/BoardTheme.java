package com.chess.engine.board;

import java.awt.Color;


//TODO: Decide to Design this Class as public or private with getter.
public class BoardTheme {
	final private Color lightColor;
	final private Color darkColor;
	
	public BoardTheme(final Color lightColor, final Color darkColor){
		this.lightColor = lightColor;
		this.darkColor = darkColor;
	}
	public final Color getLightColor() {
		return this.lightColor;
	}
	
	public final Color getDarkColor() {
		return this.darkColor;
	}
}
