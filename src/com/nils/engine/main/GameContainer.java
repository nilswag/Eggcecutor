package com.nils.engine.main;

import java.awt.Color;
import java.awt.Graphics;

import com.nils.engine.gfx.Display;
import com.nils.engine.gfx.Font;
import com.nils.engine.main.input.Input;

public class GameContainer implements Runnable{

	private int scrWidth, scrHeight;
	private int winWidth, winHeight;
	private double winScale;
	private String winTitle;
	private boolean running = false;
	
	private Thread thread;
	private Display display;
	private Input input;
	private Graphics gImg;
	private Game game;
	private Font font;
	
	private int ups, fps;
	
	public GameContainer(int scrWidth, int scrHeight, double winScale, String winTitle) {
		this.scrWidth = scrWidth;
		this.scrHeight = scrHeight;
		this.winScale = winScale;
		this.winTitle = winTitle;
		winWidth = (int) Math.floor(scrWidth * winScale);
		winHeight = (int) Math.floor(scrHeight * winScale);
		
		thread = new Thread(this);
		display = new Display(this);
		input = new Input(this);
		gImg = display.getgImg();
		font = new Font();
		game = null;
	}
	
	public void run() {
		boolean render = false;
		
		double firstTime, lastTime = System.nanoTime() / 1e9;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		final double cap = 1.0 / 60.0;
		
		double frameTime = 0;
		int frames = 0;
		
		double updateTime = 0;
		int updates = 0;
		
		while (running) {
			render = false;
			
			firstTime = System.nanoTime() / 1e9;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			updateTime += passedTime;
			
			while (unprocessedTime >= cap) {
				unprocessedTime -= cap;
				update(passedTime);
				updates++;
				
				if (frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
				
				if (updateTime >= 1.0) {
					updateTime = 0;
					ups = updates;
					updates = 0;
				}
				
				render = true;
			}
			
			if (render) {
				render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		stop();
	}
	
	private void render() {
		gImg.setColor(Color.BLACK);
		gImg.clearRect(0, 0, scrWidth, scrHeight);
		game.render(gImg);
		font.drawString(gImg, fps + " / " + ups, 5, 210, 0xffffffff);
		display.update();
	}
	
	private void update(double dt) {
		game.update(dt);
		input.update();
	}
	
	public void start() {
		running = true;
		thread.run();
	}
	
	public void stop() {
		running = false;
		display.dispose();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getScrWidth() {
		return scrWidth;
	}

	public void setScrWidth(int scrWidth) {
		this.scrWidth = scrWidth;
	}

	public int getScrHeight() {
		return scrHeight;
	}

	public void setScrHeight(int scrHeight) {
		this.scrHeight = scrHeight;
	}

	public int getWinWidth() {
		return winWidth;
	}

	public void setWinWidth(int winWidth) {
		this.winWidth = winWidth;
	}

	public int getWinHeight() {
		return winHeight;
	}

	public void setWinHeight(int winHeight) {
		this.winHeight = winHeight;
	}

	public double getWinScale() {
		return winScale;
	}

	public void setWinScale(float winScale) {
		this.winScale = winScale;
	}

	public String getWinTitle() {
		return winTitle;
	}

	public void setWinTitle(String winTitle) {
		this.winTitle = winTitle;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public Graphics getgImg() {
		return gImg;
	}

	public void setgImg(Graphics gImg) {
		this.gImg = gImg;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}
	
}
