package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardTheme;
import com.chess.engine.board.BoardUtils;

public class Table {
	private final JFrame gameFrame;
	private final BoardPanel boardPanel;
	private final Board chessBoard;
	
	private static String defaultImagePath = "images/pieces/cburnett/";

	
	private BoardTheme boardThemeBlack = new BoardTheme(new Color(200,200,200), new Color(30,30,30));
	private BoardTheme boardThemeBrown = new BoardTheme(new Color(240,217,181), new Color(181,136,99));
	private BoardTheme boardThemePurple = new BoardTheme(new Color(159,144,176), new Color(125,74,141));

	private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
	private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
	private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

	public Table() {
		this.gameFrame = new JFrame("Chess");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createTableMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.chessBoard = Board.createStandardBoard();
		
		this.boardPanel = new BoardPanel();
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

		final ImageIcon imageIcon = new ImageIcon("images/icons/icon_v2.png");
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
			for (final TilePanel tile: boardTiles) {
				tile.setBackground((tile.getTileId() + tile.getTileId() / BoardUtils.NUM_TILES_PER_ROW) % 2 == 0 ? boardTheme.getLightColor() : boardTheme.getDarkColor());
			}
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
				public void mouseClicked(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
		}
		
		public int getTileId() {
			return this.tileId;
		}
		
		private void assignTilePieceIcon(final Board board) {
			this.removeAll();
			if (board.getTile(tileId).isTileOccupied()) {
				try {
					final BufferedImage image = ImageIO.read(new File(defaultImagePath + 
							board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0, 1).toLowerCase() + 
							board.getTile(this.tileId).getPiece().toString() + ".png"));
					
					final Image scaledImage = image.getScaledInstance(BoardUtils.NUM_TILES, BoardUtils.NUM_TILES, Image.SCALE_SMOOTH);
					
					add(new JLabel(new ImageIcon(scaledImage)));
				
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}
