package com.nils.engine.gfx;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.nils.engine.main.GameContainer;

public class Display {

	private JFrame frame;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	private Graphics gImg;
	private BufferedImage image;
	
	private int x, y;
	
	public Display(GameContainer gc) {
		Dimension dim = new Dimension(gc.getWinWidth(), gc.getWinHeight());
		frame = new JFrame(gc.getWinTitle());
		canvas = new Canvas();
		
		frame.setMinimumSize(dim);
		frame.setMaximumSize(dim);
		frame.getContentPane().setPreferredSize(dim);
		frame.getContentPane().setMinimumSize(dim);
		frame.getContentPane().setMaximumSize(dim);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas.setPreferredSize(dim);
		canvas.setMinimumSize(dim);
		canvas.setMaximumSize(dim);
		
		frame.pack();
		
		canvas.createBufferStrategy(2);
		
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		image = new BufferedImage(gc.getScrWidth(), gc.getScrHeight(), BufferedImage.TYPE_INT_RGB);
		gImg = image.getGraphics();
		
		x = 0;
		y = 0;
		
		image.flush();
	}
	
	public void update() {
		g.drawImage(image, x, y, canvas.getWidth(), canvas.getHeight(), null);
		bs.show();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public BufferStrategy getBs() {
		return bs;
	}

	public void setBs(BufferStrategy bs) {
		this.bs = bs;
	}

	public Graphics getG() {
		return g;
	}

	public void setG(Graphics g) {
		this.g = g;
	}

	public Graphics getgImg() {
		return gImg;
	}

	public void setgImg(Graphics gImg) {
		this.gImg = gImg;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void dispose() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
