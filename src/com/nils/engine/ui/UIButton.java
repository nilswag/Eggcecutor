package com.nils.engine.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.nils.engine.audio.SoundClip;
import com.nils.engine.main.GameContainer;

public class UIButton {
	
	private boolean selected = false;
	private float x, y;
	private GameContainer gc;
	private ClickFunc clickFunc;
	private Rectangle rect;
	private Rectangle mouseRect;
	private BufferedImage buttonImg;
	
	private SoundClip select = new SoundClip("/button-select.wav");
	private boolean selectPlayed = false;

	public UIButton(GameContainer gc, ClickFunc clickFunc, BufferedImage buttonImg, float x, float y) {
		this.gc = gc;
		this.clickFunc = clickFunc;
		this.buttonImg = buttonImg;
		this.x = x;
		this.y = y;
		rect = new Rectangle((int) x - buttonImg.getWidth() / 2, (int) y - buttonImg.getWidth() / 2, buttonImg.getWidth(), buttonImg.getHeight());
		mouseRect = new Rectangle(gc.getInput().getMouseX(), gc.getInput().getMouseY(), 1, 1);
		select.setVolume(-10);
	}
	
	public void update(double dt) {
		mouseRect.x = gc.getInput().getMouseX();
		mouseRect.y = gc.getInput().getMouseY();
		
		if (rect.contains(mouseRect)) {
			selected = true;
			rect.width = (int) (buttonImg.getWidth() * 1.2);
			rect.height = (int) (buttonImg.getHeight() * 1.2);
			rect.x = (int) (x - rect.width / 2);
			rect.y = (int) (y - rect.height / 2);
			if (!selectPlayed) {
				select.play();
				selectPlayed = true;
			}
		} else {
			selectPlayed = false;
			rect.width = buttonImg.getWidth();
			rect.height = buttonImg.getHeight();
			selected = false;
			rect.x = (int) (x - rect.width / 2);
			rect.y = (int) (y - rect.height / 2);
		}
		
		if (gc.getInput().isButton(MouseEvent.BUTTON1) && selected) {
			clickFunc.click();
		}
	}
	
	public void render(Graphics g) {
		if (selected) { 
			g.drawImage(buttonImg, rect.x, rect.y, (int) rect.width, rect.height, null);
		} else {
			g.drawImage(buttonImg, rect.x, rect.y, null);
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
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

	public GameContainer getGc() {
		return gc;
	}

	public void setGc(GameContainer gc) {
		this.gc = gc;
	}

	public ClickFunc getClickFunc() {
		return clickFunc;
	}

	public void setClickFunc(ClickFunc clickFunc) {
		this.clickFunc = clickFunc;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public Rectangle getMouseRect() {
		return mouseRect;
	}

	public void setMouseRect(Rectangle mouseRect) {
		this.mouseRect = mouseRect;
	}

	public BufferedImage getButtonImg() {
		return buttonImg;
	}

	public void setButtonImg(BufferedImage buttonImg) {
		this.buttonImg = buttonImg;
	}
	
}
