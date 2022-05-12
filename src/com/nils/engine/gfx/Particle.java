package com.nils.engine.gfx;

import java.awt.Color;
import java.awt.Graphics;

import com.nils.engine.main.Util;

public class Particle {

	private float x, y;
	private float xVel, yVel;
	private float width, height;
	
	public Particle(float x, float y) {
		this.x = x;
		this.y = y;
		xVel = Util.randInt(-4, 4) / 2;
		yVel = Util.randInt(-4, 4) / 2;
		width = Util.randInt(1, 4);
		height = Util.randInt(1, 4);
	}
	
	public void update(double dt) {
		width -= 0.1;
		x += xVel;
		y += yVel;
	}
	
	public void render(Graphics g, Color color) {
		g.setColor(color);
		g.fillRect((int) x, (int) y, (int) width, (int) width);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getxVel() {
		return xVel;
	}

	public void setxVel(float xVel) {
		this.xVel = xVel;
	}

	public float getyVel() {
		return yVel;
	}

	public void setyVel(float yVel) {
		this.yVel = yVel;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
}
