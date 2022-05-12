package com.nils.game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.nils.engine.audio.SoundClip;
import com.nils.engine.entity.Entity;
import com.nils.engine.entity.EntityHandler;
import com.nils.engine.gfx.Animation;
import com.nils.engine.gfx.ParticleHandler;
import com.nils.engine.main.GameContainer;
import com.nils.engine.main.Util;
import com.nils.engine.state.StateHandler;

public class Player extends Entity{

	private SoundClip hurt = new SoundClip("/player-hurt.wav");
	
	private StateHandler sh;
	
	private Animation animationIdle;
	private Animation animationRunning;
	private Animation animationDeath;
	
	private Animation animationIdleUmbrella;
	private Animation animationRunningUmbrella;
	
	private BufferedImage idleSheet = Util.loadImage("/player-idle.png");
	private BufferedImage runningSheet = Util.loadImage("/player-running.png");
	private BufferedImage idleSheetUmbrella = Util.loadImage("/player-idle-umbrella.png");
	private BufferedImage runningSheetUmbrella = Util.loadImage("/player-running-umbrella.png");
	private BufferedImage deathSheet = Util.loadImage("/player-death.png");
	private BufferedImage heart = Util.loadImage("/heart.png");
	private BufferedImage heartBlack = Util.loadImage("/heart-black.png");
	
	private ParticleHandler ph;
	private Rectangle rect;
	
	private int maxHealth = 3;
	private int health = maxHealth;
	private boolean dead = false;
	
	private double dt;
	
	private double timer = 0;
	
	private int shake = 0;
	
	private boolean umbrella = false;
	private double umbrellaTimer = 0;
	
	public Player(EntityHandler eh, GameContainer gc, float x, float y, float speed, String tag, StateHandler sh) {
		super(eh, gc, x, y, speed, tag);
		this.sh = sh;
		
		animationIdle = new Animation(idleSheet, 3, 1, 32, 32, new double[] {.025, .025, .025}, 2);
		animationRunning = new Animation(runningSheet, 2, 1, 32, 32, new double[] {.02, .02}, 2);
		animationDeath = new Animation(deathSheet, 6, 1, 32, 32, new double[] {.02, .02, .02, .02, .02, .02}, 2);
		
		animationIdleUmbrella = new Animation(idleSheetUmbrella, 3, 1, 32, 32, new double[] {.025, .025, .025}, 2);
		animationRunningUmbrella = new Animation(runningSheetUmbrella, 2, 1, 32, 32, new double[] {.02, .02}, 2);
		
		ph = new ParticleHandler(gc);
		rect = new Rectangle((int) x, (int) y, 24, 42);
	}

	@Override
	public void update(double dt) {
		this.dt = dt;
		
		if (health <= 0) {
			ph.generateParticles(x + 30, y + 30, 15);
			if (timer >= .03) {
				timer = 0;
				dead = true;
			}
			timer += dt;
		}
		
		gc.getDisplay().setX(0);
		gc.getDisplay().setY(0);
		if (shake > 0) {
			shake--;
			gc.getDisplay().setX(Util.randInt(0, 16) - 4);
			gc.getDisplay().setY(Util.randInt(0, 16) - 4);
		}
		
		if (!dead) {
			if (gc.getInput().isKey(KeyEvent.VK_A)  || gc.getInput().isKey(KeyEvent.VK_LEFT)  && !(x-xVel <= -32)) {xVel = -speed; flip = true;}
			else if (gc.getInput().isKey(KeyEvent.VK_D)  || gc.getInput().isKey(KeyEvent.VK_RIGHT)   && !(x+xVel >= 320-32)) {xVel = speed; flip = false;}
			else {xVel = 0;}
				
			if (gc.getInput().isKey(KeyEvent.VK_W)  || gc.getInput().isKey(KeyEvent.VK_UP)   && !(y-yVel <= -32)) {yVel = -speed;}
			else if (gc.getInput().isKey(KeyEvent.VK_S)  || gc.getInput().isKey(KeyEvent.VK_DOWN)   && !(y+yVel >= 220-32)) {yVel = speed;}
			else {yVel = 0;}
			
			x += xVel;
			y += yVel;
			rect.x = (int) x+20;
			rect.y = (int) y+10;
			
			if (umbrella) {
				rect.y -= 13;
				umbrellaTimer += dt;
			}
			
			if (umbrellaTimer >= .5) {
				umbrellaTimer = 0;
				umbrella = false;
			}
		}
		
		ph.update(dt);
	}
	
	public void damage(Egg egg) {
		health--;
		shake = 20;
		hurt.play();
	}

	private Animation currentAnimation = animationIdle;
	
	@Override
	public void render(Graphics g) {
		currentAnimation = animationIdleUmbrella;
		if (!umbrella) {
			currentAnimation = animationIdle;
		}
		
		if (xVel != 0 || yVel != 0) {
			currentAnimation = animationRunningUmbrella;
			if (!umbrella) {
				currentAnimation = animationRunning;
			}
		}
		
		if (dead) {
			currentAnimation = animationDeath;
		}
		
		currentAnimation.render(g, dt, (int) x, (int) y, flip);
		
		ph.render(g, Color.red);
		
		for (int i = 0; i < maxHealth; i++) {
			g.drawImage(heartBlack, i*14+10, 10, 14, 14, null);
		}
		for (int i = 0; i < health; i++) {
			g.drawImage(heart, i*14+10, 10, 14, 14, null);
		}
	}

	public Animation getAnimationIdle() {
		return animationIdle;
	}

	public void setAnimationIdle(Animation animationIdle) {
		this.animationIdle = animationIdle;
	}

	public Animation getAnimationRunning() {
		return animationRunning;
	}

	public void setAnimationRunning(Animation animationRunning) {
		this.animationRunning = animationRunning;
	}

	public BufferedImage getIdleSheet() {
		return idleSheet;
	}

	public void setIdleSheet(BufferedImage idleSheet) {
		this.idleSheet = idleSheet;
	}

	public BufferedImage getRunningSheet() {
		return runningSheet;
	}

	public void setRunningSheet(BufferedImage runningSheet) {
		this.runningSheet = runningSheet;
	}

	public ParticleHandler getPh() {
		return ph;
	}

	public void setPh(ParticleHandler ph) {
		this.ph = ph;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public double getDt() {
		return dt;
	}

	public void setDt(double dt) {
		this.dt = dt;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public BufferedImage getHeart() {
		return heart;
	}

	public void setHeart(BufferedImage heart) {
		this.heart = heart;
	}

	public BufferedImage getHeartBlack() {
		return heartBlack;
	}

	public void setHeartBlack(BufferedImage heartBlack) {
		this.heartBlack = heartBlack;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public StateHandler getSh() {
		return sh;
	}

	public void setSh(StateHandler sh) {
		this.sh = sh;
	}

	public Animation getAnimationDeath() {
		return animationDeath;
	}

	public void setAnimationDeath(Animation animationDeath) {
		this.animationDeath = animationDeath;
	}

	public BufferedImage getIdleSheetUmbrella() {
		return idleSheetUmbrella;
	}

	public void setIdleSheetUmbrella(BufferedImage idleSheetUmbrella) {
		this.idleSheetUmbrella = idleSheetUmbrella;
	}

	public BufferedImage getRunningSheetUmbrella() {
		return runningSheetUmbrella;
	}

	public void setRunningSheetUmbrella(BufferedImage runningSheetUmbrella) {
		this.runningSheetUmbrella = runningSheetUmbrella;
	}

	public BufferedImage getDeathSheet() {
		return deathSheet;
	}

	public void setDeathSheet(BufferedImage deathSheet) {
		this.deathSheet = deathSheet;
	}

	public double getTimer() {
		return timer;
	}

	public void setTimer(double timer) {
		this.timer = timer;
	}

	public int getShake() {
		return shake;
	}

	public void setShake(int shake) {
		this.shake = shake;
	}

	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public Animation getAnimationIdleUmbrella() {
		return animationIdleUmbrella;
	}

	public void setAnimationIdleUmbrella(Animation animationIdleUmbrella) {
		this.animationIdleUmbrella = animationIdleUmbrella;
	}

	public Animation getAnimationRunningUmbrella() {
		return animationRunningUmbrella;
	}

	public void setAnimationRunningUmbrella(Animation animationRunningUmbrella) {
		this.animationRunningUmbrella = animationRunningUmbrella;
	}

	public boolean isUmbrella() {
		return umbrella;
	}

	public void setUmbrella(boolean umbrella) {
		this.umbrella = umbrella;
	}

}
