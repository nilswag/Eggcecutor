package com.nils.engine.ui;

import java.awt.Graphics;
import java.util.ArrayList;

public class UIButtonHandler {

	private ArrayList<UIButton> buttons;
	
	public UIButtonHandler() {
		buttons = new ArrayList<UIButton>();
	}
	
	public void update(double dt) {
		for (UIButton b : buttons) {
			b.update(dt);
		}
	}
	
	public void render(Graphics g) {
		for (UIButton b : buttons) {
			b.render(g);
		}
	}
	
	public void addButton(UIButton button) {
		buttons.add(button);
	}
	
	public void removeButton(UIButton button) {
		buttons.add(button);
	}

	public ArrayList<UIButton> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<UIButton> buttons) {
		this.buttons = buttons;
	}
	
}
