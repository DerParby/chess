package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Move.QueenSideCastleMove;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Piece.PieceType;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

public class WhitePlayer extends Player {
	public WhitePlayer(final Board board, final Collection<Move> whiteStandardLegalMoves,
			final Collection<Move> blackStandardLegalMoves) {
		super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		// TODO Auto-generated method stub
		return this.board.blackPlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
			final Collection<Move> opponentLegals) {
		final List<Move> kingCastles = new ArrayList<>();

		if (this.playerKing.isFirstMove() && !this.isInCheck()) {
			// white king side castles
			if (!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				// Rook on h1
				final Tile rookTile = this.board.getTile(63);
				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if (Player.calculatesAttacksOnTile(61, opponentLegals).isEmpty()
							&& Player.calculatesAttacksOnTile(62, opponentLegals).isEmpty()
							&& rookTile.getPiece().getPieceType().isRook()) {
						kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62,
								(Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 61));

					}
				}
			}
			// white queen side castles
			if (!this.board.getTile(57).isTileOccupied() && !this.board.getTile(58).isTileOccupied()
					&& !this.board.getTile(59).isTileOccupied()) {
				// Rook on a1
				final Tile rookTile = this.board.getTile(56);
				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if (Player.calculatesAttacksOnTile(57, opponentLegals).isEmpty()
							&& Player.calculatesAttacksOnTile(58, opponentLegals).isEmpty()
							&& Player.calculatesAttacksOnTile(59, opponentLegals).isEmpty()
							&& rookTile.getPiece().getPieceType().isRook()) {
						kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58,
								(Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
					}
				}
			}
		}
		return ImmutableList.copyOf(kingCastles);
	}
}
