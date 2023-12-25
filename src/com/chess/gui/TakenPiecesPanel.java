package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.gui.Table.MoveLog;
import com.google.common.primitives.Ints;

public class TakenPiecesPanel extends JPanel{
	
	private final JPanel northPanel;
	private final JPanel southPanel;
	private static final Color PANEL_COLOR = new Color (220,220,220);
	
	private static final Border PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
	private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40,80);

	public TakenPiecesPanel() {
		super(new BorderLayout());
		setBackground(PANEL_COLOR);
		setBorder(PANEL_BORDER);
		this.northPanel = new JPanel(new GridLayout(8,2));
		this.southPanel = new JPanel(new GridLayout(8,2));
		this.northPanel.setBackground(PANEL_COLOR);
		this.southPanel.setBackground(PANEL_COLOR);
		this.add(this.southPanel, BorderLayout.NORTH);
		this.add(this.southPanel, BorderLayout.SOUTH);
		setPreferredSize(TAKEN_PIECES_DIMENSION);
	}
	
	public void redo(final MoveLog moveLog) {
		this.southPanel.removeAll();
		this.northPanel.removeAll();
		
		final List<Piece> whiteTakenPieces = new ArrayList<>();
		final List<Piece> blackTakenPieces = new ArrayList<>();
		
		for (final Move move: moveLog.getMoves()) {
			if(move.isAttack()) {
				final Piece takenPiece = move.getAttackedPiece();
				if(takenPiece.getPieceAlliance().isWhite()) {
					whiteTakenPieces.add(takenPiece);
				} else if(takenPiece.getPieceAlliance().isBlack()) {
					blackTakenPieces.add(takenPiece);
				} else {
					throw new RuntimeException("should not reach here!");
				}
			}
		}
		
		Collections.sort(whiteTakenPieces, new Comparator<Piece>() {

			@Override
			public int compare(final Piece o1, final Piece o2) {
				// TODO Auto-generated method stub
				return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
			}
		});
		
		Collections.sort(blackTakenPieces, new Comparator<Piece>() {

			@Override
			public int compare(final Piece o1,final  Piece o2) {
				// TODO Auto-generated method stub
				return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
			}
		});
		
		for (final Piece takenPiece: whiteTakenPieces) {
			try {
				final BufferedImage image = ImageIO
						.read(new File("images/pieces/cburnett/"
								+ takenPiece.getPieceAlliance().toString()
										.substring(0, 1).toLowerCase()
								+ takenPiece.toString() + ".png"));

				final Image scaledImage = image.getScaledInstance(BoardUtils.NUM_TILES, BoardUtils.NUM_TILES,
						Image.SCALE_SMOOTH);
				
				final JLabel imageLabel = new JLabel();
				this.southPanel.add(imageLabel);
				
			} catch (final IOException e){
				e.printStackTrace();
			}
		}
		for (final Piece takenPiece: blackTakenPieces) {
			try {
				final BufferedImage image = ImageIO
						.read(new File("images/pieces/cburnett/"
								+ takenPiece.getPieceAlliance().toString()
										.substring(0, 1).toLowerCase()
								+ takenPiece.toString() + ".png"));

				final Image scaledImage = image.getScaledInstance(BoardUtils.NUM_TILES, BoardUtils.NUM_TILES,
						Image.SCALE_SMOOTH);
				
				final JLabel imageLabel = new JLabel();
				this.northPanel.add(imageLabel);
				
			} catch (final IOException e){
				e.printStackTrace();
			}
		}
		validate();
	}
}
