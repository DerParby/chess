package com.chess.engine.board;

import javax.management.RuntimeErrorException;

public class BoardUtils {
	public static final boolean[] FIRST_FILE = initColumn(0);
	public static final boolean[] SECOND_FILE = initColumn(1);
	public static final boolean[] SEVENTH_FILE = initColumn(6);
	public static final boolean[] EIGHTH_FILE = initColumn(7);
	
	public static final boolean[] EIGHTH_RANK = initRank(0);
	public static final boolean[] SEVENTH_RANK = initRank(1);
	public static final boolean[] SIXTH_RANK = initRank(2);
	public static final boolean[] FIFTH_RANK = initRank(3);
	public static final boolean[] FOURTH_RANK = initRank(4);
	public static final boolean[] THIRD_RANK = initRank(5);
	public static final boolean[] SECOND_RANK = initRank(6);
	public static final boolean[] FIRST_RANK = initRank(7);
	
	
	public static final int NUM_TILES = 64;
	public static final int NUM_TILES_PER_ROW = 8;
	
	
	private BoardUtils() {
		throw new RuntimeErrorException(null, "You cannot instantiate BoardUtils!");
	}
	
	private static boolean[] initColumn(int columnNumber) {
		final boolean[] column = new boolean[NUM_TILES];
		do {
			column[columnNumber] = true;
			columnNumber += NUM_TILES_PER_ROW;
		} while (columnNumber < NUM_TILES);
		return column;
	}
	
	private static boolean[] initRank(int rowNumber) {
		final boolean[] column = new boolean[NUM_TILES];
		rowNumber *= NUM_TILES_PER_ROW;
		do {
			column[rowNumber] = true;
			rowNumber++;
		} while (rowNumber % NUM_TILES_PER_ROW != 0);
		return column;
	}

	public static boolean isValidTileCoordinate(final int coordinate) {
		return coordinate >= 0 && coordinate < NUM_TILES;
	}

}
