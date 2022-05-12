package com.nils.game.states;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.nils.engine.audio.SoundClip;
import com.nils.engine.entity.EntityHandler;
import com.nils.engine.gfx.Font;
import com.nils.engine.main.GameContainer;
import com.nils.engine.main.Util;
import com.nils.engine.state.State;
import com.nils.engine.state.StateHandler;
import com.nils.engine.ui.ClickFunc;
import com.nils.engine.ui.UIButton;
import com.nils.engine.ui.UIButtonHandler;
import com.nils.game.entities.EggHandler;
import com.nils.game.entities.Heart;
import com.nils.game.entities.Player;
import com.nils.game.entities.PowerUpFunc;
import com.nils.game.entities.PowerUpHandler;
import com.nils.game.entities.UmbrellaHat;
import com.nils.game.main.EggGame;
import com.nils.game.map.MapHandler;

public class GameState extends State{
	
	private EntityHandler eh;
	private MapHandler mh;
	private EggHandler eggHandler;

	private Player player;
	private Font font;
	
	private double timer;
	private double dt = 0;
	
	private double dropTime = .2;
	private float score = 0;
	private double amp = 1;
	private int scoreMax = 1000;
	
	private double gameTimer2 = 0;
	
	private UIButtonHandler uiHandler;
	private PowerUpHandler puHandler;
	
	private BufferedImage buttons = Util.loadImage("/buttons.png");
	
	private boolean started = false;
	
	private BufferedImage deathScreen = Util.loadImage("/death-screen.png");
	private BufferedImage shadow = Util.loadImage("/shadow.png");
	
	private double powerUpTimer = 0;
	
	private SoundClip collect = new SoundClip("/powerup-collect.wav");
	
	public GameState(StateHandler sh, GameContainer gc, String tag) {
		super(sh, gc, tag);
		eh = new EntityHandler(gc);
		mh = new MapHandler(gc);
		eggHandler = new EggHandler(gc);
		uiHandler = new UIButtonHandler();
		puHandler = new PowerUpHandler();
		
		uiHandler.addButton(new UIButton(gc, new ClickFunc() {

			@Override
			public void click() {
				sh.setState(new GameState(sh, gc, tag));
			}
			
		}, Util.getSubimage(buttons, 0, 1, 64, 32), 160, 150));
		
		uiHandler.addButton(new UIButton(gc, new ClickFunc() {

			@Override
			public void click() {
				sh.setCurrentState("menu");
			}
			
		}, Util.getSubimage(buttons, 1, 0, 64, 32), 160, 190));
		
		player = new Player(eh, gc, 128, 78, 3, "player", sh);
		font = new Font();
		
		eh.addEntity(player);
	}
	
	private double deathTimer = 0;
	private double scoreAmp = 1; 
	
	private boolean gameRunning = false;

	@Override
	public void update(double dt) {
		
		if (gc.getInput().isKeyDown(KeyEvent.VK_SPACE)) {
			gameRunning = true;
		}
		
		if (gameRunning) {
			this.dt = dt;
			gameTimer2 += dt;
			powerUpTimer += dt;
			
			if(gc.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
				sh.setCurrentState("pause");
			}
			
			started = true;
			
			if (timer >= .2 && 1.5 * amp <= 4) {
				timer = 0;
				eggHandler.addEggs((int) (1.5 * amp), player);
			}
			
			if (powerUpTimer >= 1) {
				powerUpTimer = 0;
				
				float x = Util.randInt(0, 320);
				float y = -32;
				
				if (Util.randInt(1, 2) == 1) {
					puHandler.addPowerUp(new UmbrellaHat(gc, x, y, new PowerUpFunc() {

						@Override
						public void collision() {
							collect.play();
							player.setUmbrella(true);
						}
						
					}, player));
				} else {
					puHandler.addPowerUp(new Heart(gc, x, y, new PowerUpFunc() {

						@Override
						public void collision() {
							collect.play();
							if (!(player.getHealth() >= player.getMaxHealth())) {
								player.setHealth(player.getHealth() + 1);
							}
						}
						
					}, player));
				}
			}
			
			if (!player.isDead()) {
				timer += dt;
			
				eh.update(dt);
				eggHandler.update(dt);
				puHandler.update(dt);
			
				scoreAmp = amp*2;
				
				if (player.getRect().y <= 74) {
					scoreAmp = amp * 2;
				}
				
				score += (int) (1.5 * scoreAmp);
				
				if (score >= scoreMax) {
					amp += .2;
					scoreMax = (int) (scoreMax * 2);
				}
			} else {
				gc.getDisplay().setX(0);
				gc.getDisplay().setY(0);
				if (deathTimer >= .11) {
					uiHandler.update(dt);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		mh.render(g);
		eggHandler.render(g);
		if (!gameRunning) {
			int width = g.getFontMetrics().stringWidth("Press SPACE to start");
			g.setColor(Color.white);
			g.setFont(new java.awt.Font("Upheavel TT (BRK)", java.awt.Font.BOLD, 12));
			g.drawString("Press SPACE to start", 160 - (int) (width / 2), 140);
		}
		if (started) {
			g.setFont(new java.awt.Font("Upheavel TT (BRK)", java.awt.Font.BOLD, 10));
			int swidth = g.getFontMetrics().stringWidth(Integer.toString((int) score));
			g.setColor(Color.white);
			int scoreX = (int) player.getRect().getCenterX() - (int) (swidth / 2);
			int scoreY = player.getRect().y-5;
			boolean higherScore = player.getCurrentAnimation() == player.getAnimationIdleUmbrella() || player.getCurrentAnimation() == player.getAnimationRunningUmbrella();
			if (higherScore) {
				scoreY = player.getRect().y-10;
			}
			g.drawString(Integer.toString((int) score), scoreX, scoreY);
		}
		puHandler.render(g);
		eh.render(g);
		g.drawImage(shadow, 0, 0, null);
		if (player.isDead()) {
			deathTimer += dt;
			if (deathTimer >= .11) {
				g.drawImage(deathScreen, 0, 0, null);
				g.setColor(Color.white);
				int hwidth = g.getFontMetrics().stringWidth("Highscore: " + EggGame.highScore);
				int width = g.getFontMetrics().stringWidth("Score: " + Integer.toString((int) score));
				g.setFont(new java.awt.Font("Upheavel TT (BRK)", java.awt.Font.BOLD, 12));
				g.drawString("Score: " + Integer.toString((int) score), 160 - (int) (width / 2), 125);
				g.drawString("Highscore: " + Integer.toString(EggGame.highScore), 160 - (int) (hwidth / 2), 110);
				uiHandler.render(g);
			}
		}
	}

	public EntityHandler getEh() {
		return eh;
	}

	public void setEh(EntityHandler eh) {
		this.eh = eh;
	}

	public MapHandler getMh() {
		return mh;
	}

	public void setMh(MapHandler mh) {
		this.mh = mh;
	}

	public EggHandler getEggHandler() {
		return eggHandler;
	}

	public void setEggHandler(EggHandler eggHandler) {
		this.eggHandler = eggHandler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public double getTimer() {
		return timer;
	}

	public void setTimer(double timer) {
		this.timer = timer;
	}

	public double getDt() {
		return dt;
	}

	public void setDt(double dt) {
		this.dt = dt;
	}

	public double getDropTime() {
		return dropTime;
	}

	public void setDropTime(double dropTime) {
		this.dropTime = dropTime;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public double getAmp() {
		return amp;
	}

	public void setAmp(double amp) {
		this.amp = amp;
	}

	public int getScoreMax() {
		return scoreMax;
	}

	public void setScoreMax(int scoreMax) {
		this.scoreMax = scoreMax;
	}

	public double getGameTimer2() {
		return gameTimer2;
	}

	public void setGameTimer2(double gameTimer2) {
		this.gameTimer2 = gameTimer2;
	}

	public UIButtonHandler getUiHandler() {
		return uiHandler;
	}

	public void setUiHandler(UIButtonHandler uiHandler) {
		this.uiHandler = uiHandler;
	}

	public BufferedImage getButtons() {
		return buttons;
	}

	public void setButtons(BufferedImage buttons) {
		this.buttons = buttons;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public BufferedImage getDeathScreen() {
		return deathScreen;
	}

	public void setDeathScreen(BufferedImage deathScreen) {
		this.deathScreen = deathScreen;
	}

	public BufferedImage getShadow() {
		return shadow;
	}

	public void setShadow(BufferedImage shadow) {
		this.shadow = shadow;
	}

	public double getDeathTimer() {
		return deathTimer;
	}

	public void setDeathTimer(double deathTimer) {
		this.deathTimer = deathTimer;
	}

	public double getScoreAmp() {
		return scoreAmp;
	}

	public void setScoreAmp(double scoreAmp) {
		this.scoreAmp = scoreAmp;
	}

	public boolean isGameRunning() {
		return gameRunning;
	}

	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}

}
