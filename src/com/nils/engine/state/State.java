package com.nils.engine.state;

import java.awt.Graphics;

import com.nils.engine.main.GameContainer;

public abstract class State {
	
	protected StateHandler sh;
	protected GameContainer gc;
	protected String tag;
	
	public State(StateHandler sh, GameContainer gc, String tag) {
		this.sh = sh;
		this.gc = gc;
		this.tag = tag;
	}
	
	public abstract void update(double dt);
	
	public abstract void render(Graphics g);

	public StateHandler getSh() {
		return sh;
	}

	public void setSh(StateHandler sh) {
		this.sh = sh;
	}

	public GameContainer getGc() {
		return gc;
	}

	public void setGc(GameContainer gc) {
		this.gc = gc;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
