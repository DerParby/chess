package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public class King extends Piece{
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};
	
	public King(final int piecePosition, final Alliance pieceAlliance) {
		super(PieceType.KING, piecePosition, pieceAlliance);
	}
	
	@Override
	public Collection<Move> calculateLegalMoves(final Board board){
		final List<Move> legalMoves = new ArrayList<>();
		for (final int currentCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition + currentCoordinateOffset;
			if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				if(isFirstColumnExclusion(this.piecePosition, currentCoordinateOffset) || 
				   isEighthColumnExclusion(this.piecePosition, currentCoordinateOffset)) {
					continue;
				}
				// TODO: More Conditions for checking whether the king is in check.
				
				
				if (!candidateDestinationTile.isTileOccupied()) {
					// No piece at tile
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				} else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					if(this.pieceAlliance != pieceAlliance) {
						// Enemy Piece at tile
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
					}
				}
				
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	@Override
	public String toString() {
		return PieceType.KING.toString();
	}
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7 || candidateOffset == -1); 
	}
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9 || candidateOffset == 1); 
	}
	
	@Override
	public King movePiece(Move move) {
		return new King(move.getDestinationCoordinate(), move.getMovedPiece().pieceAlliance);
	}
	
}
