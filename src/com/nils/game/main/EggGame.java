package com.nils.game.main;


import java.awt.Graphics;

import com.nils.engine.audio.SoundClip;
import com.nils.engine.main.Game;
import com.nils.engine.main.GameContainer;
import com.nils.engine.state.StateHandler;
import com.nils.game.states.GameState;
import com.nils.game.states.MenuState;
import com.nils.game.states.PauseState;

public class EggGame extends Game{
	
	private StateHandler sh;
	
	private MenuState menuState;
	private GameState gameState;
	private PauseState pauseState;
	private SoundClip music = new SoundClip("/music.wav");
	
	public static int highScore = 0;
	
	public EggGame(GameContainer gc) {
		super(gc);
		music.setVolume(-10);
		music.loop();
		sh = new StateHandler(gc);
		menuState = new MenuState(sh, gc, "menu");
		pauseState = new PauseState(sh, gc, "pause");
		sh.setState(menuState);
		sh.setState(pauseState);
		sh.setCurrentState("menu");
	}

	@Override
	public void update(double dt) {
		gameState = (GameState) sh.getState("game");
		try {
			if (gameState.getScore() >= highScore) {
				highScore = (int) gameState.getScore();
			}
		} catch(Exception e) {}
		sh.update(dt);
		
	}
	
	@Override
	public void render(Graphics g) {
		sh.render(g);
		
	}
	
	public static void main(String[] args) {
		GameContainer gc = new GameContainer(320, 220, 3.5, "Eggcecutor");
		EggGame game = new EggGame(gc);
		gc.setGame(game);
		gc.start();
	}

	public StateHandler getSh() {
		return sh;
	}

	public void setSh(StateHandler sh) {
		this.sh = sh;
	}

	public MenuState getMenuState() {
		return menuState;
	}

	public void setMenuState(MenuState menuState) {
		this.menuState = menuState;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public PauseState getPauseState() {
		return pauseState;
	}

	public void setPauseState(PauseState pauseState) {
		this.pauseState = pauseState;
	}

	public SoundClip getMusic() {
		return music;
	}

	public void setMusic(SoundClip music) {
		this.music = music;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
}
