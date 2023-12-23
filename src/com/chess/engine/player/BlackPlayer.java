package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

public class BlackPlayer extends Player {
	public BlackPlayer(final Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLegalMoves ) {
		super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
	}
	@Override
	public Collection<Piece> getActivePieces() {
		// TODO Auto-generated method stub
		return this.board.getBlackPieces();
	}
	
	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}
}
