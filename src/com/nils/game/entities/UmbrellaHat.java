package com.nils.game.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.nils.engine.audio.SoundClip;
import com.nils.engine.main.GameContainer;

public class UmbrellaHat extends PowerUp{

	private float fallingDistance;
	
	public UmbrellaHat(GameContainer gc, float x, float y, PowerUpFunc collisionFunc, Player player) {
		super(gc, "/umbrella-powerup-icon.png", x, y, collisionFunc, player);
		yVel = 2;
		landingX = x;
		landingY = 230;
	}
	
	private double fallTimer = 0;
	private double iconTimer = 0;
	
	private boolean collisionPlayed = false;
	
	private int w = rect.width;
	private int h = rect.height;

	@Override
	public void update(double dt) {
		fallTimer += dt;
		iconTimer += dt;
		
		if (!collected) {
			rect.x = (int) x;
			rect.y = (int) y;
		}
		
		if (fallTimer <= .2) {
			return;
		}
		
		ph.update(dt);
		
		x += (int) xVel;
		if (!(y >= landingY)) {
			y += (int) yVel;
		}
		
		if (y >= landingY) {
			landedTimer += dt;
		}
		
		if (player.getRect().intersects(rect)) {
			if (!collected) {
				ph.generateParticles((int) rect.getCenterX(), (int) rect.getCenterY(), 15);
				collisionFunc.collision();
			}
			collected = true;
		}
		
		if (collected) {
			if (ph.getParticles().size() <= 0) {
				collectedTimer += dt;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if (fallTimer <= .2) {
			g.drawImage(exclamation, (int) rect.getCenterX(), 10, null);
		}
		if (!collected) {
			g.drawImage(image, (int) rect.getCenterX(), (int) y, null);
		}
		
		ph.render(g, Color.gray);
	}

}
