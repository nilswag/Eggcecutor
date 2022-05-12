package com.nils.game.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.nils.engine.audio.SoundClip;
import com.nils.engine.gfx.ParticleHandler;
import com.nils.engine.main.GameContainer;
import com.nils.engine.main.Util;

public abstract class PowerUp {
	
	protected BufferedImage image;
	protected PowerUpFunc collisionFunc;
	protected float x, y;
	protected Rectangle rect;
	protected float xVel, yVel;
	protected boolean landed = false;
	protected float landingX, landingY;
	protected Player player;
	protected double landedTimer = 0;
	protected BufferedImage exclamation = Util.loadImage("/exclamation.png");
	protected ParticleHandler ph;
	protected boolean collected = false;
	protected double collectedTimer = 0;
	
	public PowerUp(GameContainer gc, String path, float x, float y, PowerUpFunc collisionFunc, Player player) {
		image = Util.loadImage(path);
		this.x = x;
		this.y = y;
		this.collisionFunc = collisionFunc;
		this.player = player;
		rect = new Rectangle((int) x, (int) y, image.getWidth(), image.getHeight());
		ph = new ParticleHandler(gc);
	}
	
	public abstract void update(double dt);
	
	public abstract void render(Graphics g);

}
