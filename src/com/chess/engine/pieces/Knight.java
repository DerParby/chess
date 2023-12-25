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

public class Knight extends Piece{

	private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};
	
	public Knight(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		super(PieceType.KNIGHT, piecePosition, pieceAlliance, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		List<Move>legalMoves = new ArrayList<>();
		for(final int currentCandidateOffset:CANDIDATE_MOVE_COORDINATES) {
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset)||
					isSecondColumnExclusion(this.piecePosition, currentCandidateOffset)||
					isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
					isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)){
					continue;
				}				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
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
		return PieceType.KNIGHT.toString();
	}
	
	public static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_FILE[currentPosition] && 
					(candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15);
	}
	public static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_FILE[currentPosition] && 
					 (candidateOffset == -10 || candidateOffset == 6);
	}
	public static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_FILE[currentPosition] && 
					(candidateOffset == -6 || candidateOffset == 10);
	}
	public static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_FILE[currentPosition] && 
					(candidateOffset == -15 || candidateOffset == -6 || candidateOffset == 10 || candidateOffset == 17);
	}
	
	@Override
	public Knight movePiece(final Move move) {
		return new Knight(move.getDestinationCoordinate(), move.getMovedPiece().pieceAlliance, false);
	}
}
