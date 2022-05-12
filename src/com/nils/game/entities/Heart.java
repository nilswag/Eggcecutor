package com.nils.game.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.nils.engine.audio.SoundClip;
import com.nils.engine.main.GameContainer;

public class Heart extends PowerUp{
	
	private double fallTimer = 0;
	
	public Heart(GameContainer gc, float x, float y, PowerUpFunc collisionFunc, Player player) {
		super(gc, "/heart-powerup-icon.png", x, y, collisionFunc, player);
		yVel = 2;
		landingX = x;
		landingY = 230;
	}

	@Override
	public void update(double dt) {
		fallTimer += dt;
		
		if (fallTimer <= .2) {
			return;
		}
		
		rect.x = (int) x;
		rect.y = (int) y;
		
		x += (int) xVel;
		if (!(y >= landingY)) {
			y += (int) yVel;
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
		
		ph.update(dt);
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
