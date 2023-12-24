package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

public abstract class Player {
	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	private final boolean isInCheck;
	Player(final Board board,
		Collection<Move> legalMoves,
		Collection<Move> opponentMoves){
		this.board = board;
		this.legalMoves = legalMoves;
		this.playerKing = establishKing();
		this.isInCheck = !Player.calculatesAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
	}

	private static Collection<Move> calculatesAttacksOnTile(final Integer piecePosition, final Collection<Move> moves) {
		final List<Move> attackMoves = new ArrayList<>();
		for (final Move move: moves) {
			if (piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		return ImmutableList.copyOf(attackMoves);
	}

	private King establishKing() {
		for (final Piece piece:getActivePieces()) {
			if (piece.getPieceType().isKing()) {
				return (King) piece;
			}
		}
		throw new RuntimeException("Should not reach here! Not a valid board!");
	}
	
	protected  boolean hasEscapeMoves() {
		for(final Move move:this.legalMoves) {
			final MoveTransition transition = makeMove(move);
			if (transition.getMoveStatus().isDone()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move);
	}
	
	public boolean isInCheck() {
		return this.isInCheck;
	}
	
	public boolean isInCheckMate() {
		return this.isInCheck() && !hasEscapeMoves();
	}

	public boolean isInStaleMate() {
		return !this.isInCheck() && !hasEscapeMoves();
	}
	
	public boolean isCastled() {
		//TODO: Implement Functionality 
		return false;
	}
	
	public MoveTransition makeMove(final Move move) {
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		
		final Board transitionBoard = move.execute();
		
		final Collection<Move> kingAttacks = Player.calculatesAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
				transitionBoard.currentPlayer().getLegalMoves());
		
		if (!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
		}
		
		
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}
	
	public Collection<Move> getLegalMoves() {
		return this.legalMoves;
	}

	public Piece getPlayerKing() {
		return this.playerKing;
	}

	public abstract Collection<Piece> getActivePieces(); 
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
}
