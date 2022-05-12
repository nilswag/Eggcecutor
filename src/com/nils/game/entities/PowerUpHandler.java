package com.nils.game.entities;

import java.awt.Graphics;
import java.util.ArrayList;

public class PowerUpHandler {

	private ArrayList<PowerUp> powerUps;
	
	public PowerUpHandler() {
		powerUps = new ArrayList<PowerUp>();
	}
	
	private ArrayList<PowerUp> powerUpsCopy;
	
	public void update(double dt) {
		powerUpsCopy = (ArrayList<PowerUp>) powerUps.clone();
		for (PowerUp p : powerUpsCopy) {
			p.update(dt);
			if (p.landedTimer >= .15 || p.collectedTimer >= .05) {
				powerUps.remove(p);
			}
		}
	}
	
	public void render(Graphics g) {
		for (PowerUp p : powerUps) {
			p.render(g);
		}
	}
	
	public void addPowerUp(PowerUp p) {
		powerUps.add(p);
	}

	public ArrayList<PowerUp> getPowerUps() {
		return powerUps;
	}

	public void setPowerUps(ArrayList<PowerUp> powerUps) {
		this.powerUps = powerUps;
	}
	
}
