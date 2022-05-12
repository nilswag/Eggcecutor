package com.nils.game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.nils.engine.gfx.ParticleHandler;
import com.nils.engine.main.GameContainer;
import com.nils.engine.main.Util;

public class EggHandler {

	private GameContainer gc;
	private ParticleHandler ph;
	
	private ArrayList<Egg> eggs;
	private ArrayList<Egg> eggsCopy;
	
	public EggHandler(GameContainer gc) {
		this.gc = gc;
		eggs = new ArrayList<Egg>();
		eggsCopy = new ArrayList<Egg>();
		ph = new ParticleHandler(gc);
	}
	
	public void update(double dt) {
		eggsCopy = (ArrayList<Egg>) eggs.clone();
		for (Egg e : eggsCopy) {
			e.update(dt);
			if (e.getTimer() >= .02) {
				eggs.remove(e);
			}
		}
		ph.update(dt);
	}
	
	public void render(Graphics g) {
		for (Egg e : eggsCopy) {
			e.render(g);
		}
		ph.render(g, Color.white);
	}
	
	public void addEggs(int amount, Player player) {
		for (int i = 0; i < amount; i++) {
			float x = Util.randInt(0, 320);
			float y = -32;
			eggs.add(new Egg(gc, x, y, ph, player));
		}
	}
	
}
