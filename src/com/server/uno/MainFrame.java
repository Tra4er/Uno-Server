package com.server.uno;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1435365020828715890L;
	
	public static final int DEFAULT_SCREEN_WIDTH = 800;
	public static final int DEFAULT_SCREEN_HEIGHT = 500;
	
	
	
	public MainFrame(){
		initMainFrame();
	}
	
	private void initMainFrame() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension dem = kit.getScreenSize();

		setLocation(dem.width / 3, dem.height / 4);
		setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
