package com.chess.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import static javax.swing.SwingUtilities.*;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardTheme;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;
import com.google.common.collect.Lists;

public class Table {
	private final JFrame gameFrame;
	private final BoardPanel boardPanel;
	private Board chessBoard;
	private Tile sourceTile;
	private Tile destinationTile;
	private Piece humanMovedPiece;
	private boolean highlightLegalMoves = true;
	
	private static String defaultPiecesImagePath = "images/pieces/cburnett/";
	private static String dotLegalMoveImagePath = "images/misc/dotLegalMoveBorderedDarkGreen.png";

	private BoardDirection boardDirection;

	private BoardTheme boardThemeBlack = new BoardTheme(new Color(200, 200, 200), new Color(30, 30, 30));
	private BoardTheme boardThemeBrown = new BoardTheme(new Color(240, 217, 181), new Color(181, 136, 99));
	private BoardTheme boardThemePurple = new BoardTheme(new Color(159, 144, 176), new Color(125, 74, 141));

	private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
	private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
	private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

	public Table() {
		this.gameFrame = new JFrame("Chess");
		this.gameFrame.setLayout(new BorderLayout());
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.chessBoard = Board.createStandardBoard();
		this.boardDirection = BoardDirection.NORMAL;
		this.boardPanel = new BoardPanel();
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

		final JMenuBar tableMenuBar = createTableMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		final ImageIcon imageIcon = new ImageIcon("images/pieces/cburnett/wN.png");
		this.gameFrame.setIconImage(imageIcon.getImage());
		this.gameFrame.setResizable(false);
		this.gameFrame.setVisible(true);
	}

	private JMenuBar createTableMenuBar() {
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		tableMenuBar.add(createEditMenu());
		return tableMenuBar;
	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open UP PGN File!");

			}
		});
		fileMenu.add(openPGN);

		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);

		return fileMenu;
	}

	private JMenu createEditMenu() {
		final JMenu editMenu = new JMenu("Edit");
		final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
		flipBoardMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boardDirection = boardDirection.opposite();
				boardPanel.drawBoard(chessBoard);

			}
		});
		editMenu.add(flipBoardMenuItem);

		final JMenu themeMenu = new JMenu("Board Theme");
		final JMenuItem themeBlackMenuItem = new JMenuItem("Black");
		themeBlackMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boardPanel.setTheme(boardThemeBlack);
			}
		});
		final JMenuItem themeBrownMenuItem = new JMenuItem("Brown");
		themeBrownMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boardPanel.setTheme(boardThemeBrown);
			}
		});

		final JMenuItem themePurpleMenuItem = new JMenuItem("Purple");
		themePurpleMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boardPanel.setTheme(boardThemePurple);
			}
		});
		themeMenu.add(themeBlackMenuItem);
		themeMenu.add(themeBrownMenuItem);
		themeMenu.add(themePurpleMenuItem);
		editMenu.add(themeMenu);
		
		editMenu.addSeparator();
		final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Highlight Legal Moves", highlightLegalMoves);
		legalMoveHighlighterCheckbox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();
				boardPanel.drawBoard(chessBoard);
				
			}
		});
		editMenu.add(legalMoveHighlighterCheckbox);
		
		return editMenu;

	}

	private class BoardPanel extends JPanel {
		final List<TilePanel> boardTiles;

		BoardPanel() {
			super(new GridLayout(8, 8));
			this.boardTiles = new ArrayList<>();
			for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
				final TilePanel tilePanel = new TilePanel(this, i);
				this.boardTiles.add(tilePanel);
				add(tilePanel);
			}
			setPreferredSize(BOARD_PANEL_DIMENSION);
			setTheme(boardThemeBrown);
			validate();
		}

		private void setTheme(final BoardTheme boardTheme) {
			for (final TilePanel tile : boardTiles) {
				tile.setBackground((tile.getTileId() + tile.getTileId() / BoardUtils.NUM_TILES_PER_ROW) % 2 == 0
						? boardTheme.getLightColor()
						: boardTheme.getDarkColor());
			}
		}

		public void drawBoard(final Board board) {
			removeAll();
			for (final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
				tilePanel.drawTile(board);
				add(tilePanel);
			}
			System.out.println(board);
			validate();
			repaint();
		}

	}

	public static class MoveLog{
		private final List<Move> moves;
		MoveLog(){
			this.moves = new ArrayList<>();
		}
		
		public List<Move> getMoves(){
			return this.moves;
		}
		
		public void addMove(final Move move) {
			moves.add(move);
		}
		
		public int size() {
			return this.moves.size();
		}
		
		public void clear() {
			this.moves.clear();
		}
		public Move removeMove(final int index) {
			return this.moves.remove(index);
		}
		
		
		public boolean removeMove(final Move move) {
			return this.moves.remove(move);
		}
	}
	
	
	private class TilePanel extends JPanel {
		private final int tileId;

		TilePanel(final BoardPanel boardPanel, final int tileId) {
			super(new GridBagLayout());
			this.tileId = tileId;
			setPreferredSize(TILE_PANEL_DIMENSION);
			validate();
			assignTilePieceIcon(chessBoard);
			// highlightLegals(chessBoard);
			addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(final MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(final MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(final MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(final MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(final MouseEvent event) {

					if (isLeftMouseButton(event)) {
						if (sourceTile == null) {
							// first click
							sourceTile = chessBoard.getTile(tileId);
							humanMovedPiece = sourceTile.getPiece();
							if (humanMovedPiece == null) {
								sourceTile = null;
							}
						} else {
							// second click
					
							destinationTile = chessBoard.getTile(tileId);
							for (final Move legalMoves : humanMovedPiece.calculateLegalMoves(chessBoard)) {
								if (!(legalMoves.getDestinationCoordinate() == destinationTile.getTileCoordinate())) {
									continue;
								}
								final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(),
										destinationTile.getTileCoordinate());
								final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
								if (transition.getMoveStatus().isDone()) {
									chessBoard = transition.getTransitionBoard();
									// TODO: Add move to move log
									sourceTile = null;
									destinationTile = null;
									humanMovedPiece = null;
								}
								break;
							}	
							
							
						}
					} else if (isRightMouseButton(event)) {
						sourceTile = null;
						destinationTile = null;
						humanMovedPiece = null;

					}
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							boardPanel.drawBoard(chessBoard);

						}
					});
				}
			});
			validate();
			repaint();
		}

		public void drawTile(final Board board) {
			assignTilePieceIcon(board);
			highlightLegals(board);
			validate();
			repaint();
		}

		private void highlightLegals(final Board board) {
			if (highlightLegalMoves) {
				for (final Move move : pieceLegalMoves(board)) {
					if(move.getDestinationCoordinate() == this.tileId) {
						try {
							final BufferedImage image = ImageIO.read(new File(dotLegalMoveImagePath));

							final Image scaledImage = image.getScaledInstance(20, 20,
									Image.SCALE_SMOOTH);
							add (new JLabel(new ImageIcon (scaledImage)));
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

		}

		private Collection<Move> pieceLegalMoves(final Board board) {
			if (humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {
				return humanMovedPiece.calculateLegalMoves(board);
			}
			return Collections.emptyList();
		}

		public int getTileId() {
			return this.tileId;
		}

		private void assignTilePieceIcon(final Board board) {
			this.removeAll();
			if (board.getTile(tileId).isTileOccupied()) {
				try {
					final BufferedImage image = ImageIO
							.read(new File(defaultPiecesImagePath
									+ board.getTile(this.tileId).getPiece().getPieceAlliance().toString()
											.substring(0, 1).toLowerCase()
									+ board.getTile(this.tileId).getPiece().toString() + ".png"));

					final Image scaledImage = image.getScaledInstance(BoardUtils.NUM_TILES, BoardUtils.NUM_TILES,
							Image.SCALE_SMOOTH);

					add(new JLabel(new ImageIcon(scaledImage)));

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public enum BoardDirection {
		NORMAL {
			@Override
			List<TilePanel> traverse(List<TilePanel> boardTiles) {
				return boardTiles;
			}

			@Override
			BoardDirection opposite() {
				return FLIPPED;
			}

		},
		FLIPPED {
			@Override
			List<TilePanel> traverse(List<TilePanel> boardTiles) {
				return Lists.reverse(boardTiles);
			}

			@Override
			BoardDirection opposite() {
				return NORMAL;
			}

		};

		abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);

		abstract BoardDirection opposite();

	}
}
