package com.chess.engine;

import com.chess.engine.board.Board;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

public enum Alliance {
	WHITE {
		@Override
		public int getDirection() {
			// TODO Auto-generated method stub
			return -1;
		}

		@Override
		public boolean isBlack() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isWhite() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
			// TODO Auto-generated method stub
			return whitePlayer;
		}

		@Override
		public Player getPlayer(Board board) {
			// TODO Auto-generated method stub
			return board.whitePlayer();
		}
	},	
	BLACK{
		@Override
		public int getDirection() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public boolean isBlack() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isWhite() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
			// TODO Auto-generated method stub
			return blackPlayer;
		}

		@Override
		public Player getPlayer(Board board) {
			// TODO Auto-generated method stub
			return board.blackPlayer();
		}
	};
	public abstract int getDirection();
	public abstract boolean isBlack();
	public abstract boolean isWhite();
	public abstract Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);
	public abstract Player getPlayer(final Board board);
	
}
