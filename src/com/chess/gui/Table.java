package com.chess.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;

public class Table {
	private final JFrame gameFrame;
	private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
	
	
	public Table() {
		this.gameFrame = new JFrame("Chess");
		final JMenuBar tableMenuBar = new JMenuBar();
		populateMenuBar(tableMenuBar);
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
				
		final ImageIcon imageIcon = new ImageIcon("C:\\Users\\falko\\eclipse-workspace\\Chess\\images\\icon_v2.png");
		this.gameFrame.setIconImage(imageIcon.getImage());
		this.gameFrame.setResizable(false);
		this.gameFrame.setVisible(true);
	}


	private void populateMenuBar(final JMenuBar tableMenuBar) {
		tableMenuBar.add(createFileMenu());
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
		return fileMenu;
	}
}
