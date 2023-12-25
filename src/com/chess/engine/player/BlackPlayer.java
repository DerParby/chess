package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Move.QueenSideCastleMove;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class BlackPlayer extends Player {
	public BlackPlayer(final Board board, final Collection<Move> whiteStandardLegalMoves,
			final Collection<Move> blackStandardLegalMoves) {
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

	@Override
	public Player getOpponent() {
		// TODO Auto-generated method stub
		return this.board.whitePlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {
		final List<Move> kingCastles = new ArrayList<>();

		if (this.playerKing.isFirstMove() && !this.isInCheck()) {
			// White King Side Castle Checking whether squares are obstructed. (f8, g8)
			if (!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
				// Rook on h1
				final Piece kingSideRook = this.board.getPiece(7);
				if (kingSideRook != null && kingSideRook.isFirstMove()) {
					if (Player.calculatesAttacksOnTile(5, opponentLegals).isEmpty()
							&& Player.calculatesAttacksOnTile(6, opponentLegals).isEmpty()
							&& kingSideRook.getPieceType().isRook()) {
						kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6,
								(Rook) kingSideRook, 7, 5));

					}
				}
			}
			// White Queen Side Castle Checking whether squares are obstructed. (b8, c8, d8)
			if (!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied()
					&& !this.board.getTile(3).isTileOccupied()) {
				// Rook on h1
				final Tile rookTile = this.board.getTile(0);
				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if (Player.calculatesAttacksOnTile(1, opponentLegals).isEmpty()
							&& Player.calculatesAttacksOnTile(2, opponentLegals).isEmpty()
							&& Player.calculatesAttacksOnTile(3, opponentLegals).isEmpty()
							&& rookTile.getPiece().getPieceType().isRook()) {
						kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 2,
								(Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
					}
				}
			}
		}
		return ImmutableList.copyOf(kingCastles);
	}
}
