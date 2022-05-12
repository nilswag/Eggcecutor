package com.nils.engine.main;

import java.awt.Graphics;

public abstract class Game {

	protected GameContainer gc;
	
	public Game(GameContainer gc) {
		this.gc = gc;
	}
	
	public abstract void update(double dt);
	
	public abstract void render(Graphics g);

	public GameContainer getGc() {
		return gc;
	}

	public void setGc(GameContainer gc) {
		this.gc = gc;
	}
	
}
