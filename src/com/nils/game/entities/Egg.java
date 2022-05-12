package com.nils.game.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.nils.engine.audio.SoundClip;
import com.nils.engine.gfx.ParticleHandler;
import com.nils.engine.main.GameContainer;
import com.nils.engine.main.Util;

public class Egg{
	
	private ParticleHandler ph;
	private Player player;
	private Rectangle rect;
	
	private GameContainer gc;
	
	private SoundClip explosion = new SoundClip("/eggExplosion.wav");
	private SoundClip hurt = new SoundClip("/hit-sound.wav");
	
	private BufferedImage egg = Util.loadImage("/egg.png");
	private BufferedImage eggBroken = Util.loadImage("/egg-broken.png");
	private BufferedImage eggShadow = Util.loadImage("/egg-shadow.png");
	private BufferedImage curImg = egg;
	
	private float landingX, landingY;
	private int fallingDistance;
	
	private double timer = 0;
	
	private float xVel, yVel;
	private float x, y;
	private boolean broken = false;
	
	private double fallTimer = 0;

	public Egg(GameContainer gc, float x, float y, ParticleHandler ph, Player player) {
		this.gc = gc;
		this.x = x;
		this.y = y;
		this.ph = ph;
		this.player = player;
		fallingDistance = Util.randInt(64, 230);
		landingX = x;
		landingY = y + fallingDistance;
		xVel = 0;
		yVel = Util.randInt(1, 3);
		rect = new Rectangle((int) x+3, (int) y+3, 11, 15);
		
		explosion.setVolume(-10);
	}

	int shake = 0;
	
	private boolean explosionPlayed = false;
	private float fallSpeed = Util.randInt(2, 4);
	
	public void update(double dt) {
		fallTimer += dt;
		
		rect.x = (int) x;
		rect.y = (int) y;
		
		if (fallTimer <= .2) {
			return;
		}
		
		yVel += fallSpeed / 10;
		if (!(y >= landingY) && !(broken)) {
			if (player.getRect().intersects(rect)) {
				
				if (!gc.getInput().isKey(KeyEvent.VK_P)) {
					if (!player.isUmbrella()) {
						player.damage(this);
					}
				}
				if (!explosionPlayed) {
					explosion.play();
					explosionPlayed = true;
				}
				broken = true;
			}
			y += yVel;
		} else {broken = true;}
		
		if (broken) {
			if (!explosionPlayed) {
				explosion.play();
				explosionPlayed = true;
			}
			ph.generateParticles(x + 8, y + 8, 10);
			curImg = eggBroken;
			timer += dt;
		}
	}

	public void render(Graphics g) {
		g.drawImage(curImg, (int) x, (int) y, null);
		if (!broken) {
			g.drawImage(eggShadow, (int) rect.getCenterX() - 11, (int) landingY + 7, null);
		}
	}

	public ParticleHandler getPh() {
		return ph;
	}

	public void setPh(ParticleHandler ph) {
		this.ph = ph;
	}

	public BufferedImage getEgg() {
		return egg;
	}

	public void setEgg(BufferedImage egg) {
		this.egg = egg;
	}

	public BufferedImage getEggBroken() {
		return eggBroken;
	}

	public void setEggBroken(BufferedImage eggBroken) {
		this.eggBroken = eggBroken;
	}

	public float getLandingX() {
		return landingX;
	}

	public void setLandingX(float landingX) {
		this.landingX = landingX;
	}

	public float getLandingY() {
		return landingY;
	}

	public void setLandingY(float landingY) {
		this.landingY = landingY;
	}

	public int getFallingDistance() {
		return fallingDistance;
	}

	public void setFallingDistance(int fallingDistance) {
		this.fallingDistance = fallingDistance;
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

	public boolean isBroken() {
		return broken;
	}

	public void setBroken(boolean broken) {
		this.broken = broken;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public BufferedImage getCurImg() {
		return curImg;
	}

	public void setCurImg(BufferedImage curImg) {
		this.curImg = curImg;
	}

	public double getTimer() {
		return timer;
	}

	public void setTimer(double timer) {
		this.timer = timer;
	}

}
