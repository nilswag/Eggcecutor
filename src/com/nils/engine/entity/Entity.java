package com.nils.engine.entity;

import java.awt.Graphics;

import com.nils.engine.main.GameContainer;

public abstract class Entity {
	
	protected GameContainer gc;
	protected EntityHandler eh;
	protected String tag;
	protected float xVel, yVel;
	protected float x, y;
	protected float speed;
	protected boolean flip = false;
	
	public Entity(EntityHandler eh, GameContainer gc, float x, float y, float speed, String tag) {
		this.eh = eh;
		this.gc = gc;
		this.tag = tag;
		this.x = x;
		this.y = y;
		this.speed = speed;
		xVel = speed;
		yVel = speed;
	}
	
	public abstract void update(double dt);
	
	public abstract void render(Graphics g);

	public GameContainer getGc() {
		return gc;
	}

	public void setGc(GameContainer gc) {
		this.gc = gc;
	}

	public EntityHandler getEh() {
		return eh;
	}

	public void setEh(EntityHandler eh) {
		this.eh = eh;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
