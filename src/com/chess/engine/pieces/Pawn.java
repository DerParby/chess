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
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece{
	
	private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16};
	
	public Pawn (final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove){
		super(PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATE) {
			final int candidateDestinationCoordinate = this.piecePosition + (currentCandidateOffset * this.getPieceAlliance().getDirection());
			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			// Push Pawn One Square
			if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				// TODO: Pawn Promotion.				
				legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
			} else 
			// Push Pawn Two Squares
				if (currentCandidateOffset == 16 && this.isFirstMove() && 
					(BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack()) || 
					(BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite())) {
				final int beforeCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
				if(!board.getTile(beforeCandidateDestinationCoordinate).isTileOccupied() && 
				   !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				}
			} else 
			// First Possible Attack Direction
				if((currentCandidateOffset == 7) && 
					!(BoardUtils.EIGHTH_FILE[this.piecePosition] && this.pieceAlliance.isWhite() ||
					  BoardUtils.FIRST_FILE[this.piecePosition] && this.pieceAlliance.isBlack())) {
					if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
						final Piece attackedPiece = board.getTile(candidateDestinationCoordinate).getPiece();
						if (attackedPiece.getPieceAlliance()!= this.pieceAlliance) {
							// TODO: Attack to Pawn Promotion
							legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, attackedPiece));
						}
					}
				
			} else 
			// Second Possible Attack Direction
				if((currentCandidateOffset == 9)  && 
				!(BoardUtils.EIGHTH_FILE[this.piecePosition] && this.pieceAlliance.isBlack() ||
				  BoardUtils.FIRST_FILE[this.piecePosition] && this.pieceAlliance.isWhite())) {
				if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece attackedPiece = board.getTile(candidateDestinationCoordinate).getPiece();
					if (attackedPiece.getPieceAlliance()!= this.pieceAlliance) {
						// TODO: Attack to Pawn Promotion
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, attackedPiece));
					}
				}
			}
		}
		
		return ImmutableList.copyOf(legalMoves);	
		}
	
	@Override
	public String toString() {
		return PieceType.PAWN.toString();
	}
	
	@Override
	public Pawn movePiece(final Move move) {
		return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().pieceAlliance, false);
	}
}
