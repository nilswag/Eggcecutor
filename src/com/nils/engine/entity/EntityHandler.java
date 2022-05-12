package com.nils.engine.entity;

import java.awt.Graphics;
import java.util.HashMap;

import com.nils.engine.main.GameContainer;

public class EntityHandler {
	
	private GameContainer gc;
	private HashMap<String, Entity> entities;
	
	public EntityHandler(GameContainer gc) {
		this.gc = gc;
		entities = new HashMap<String, Entity>();
	}

	public void update(double dt) {
		for (Entity e : entities.values()) {
			e.update(dt);
		}
	}
	
	public void render(Graphics g) {
		for (Entity e : entities.values()) {
			e.render(g);
		}
	}
	
	public void addEntity(Entity e) {
		entities.put(e.getTag(), e);
	}
	
	public void getEntity(String tag) {
		entities.get(tag);
	}

	public GameContainer getGc() {
		return gc;
	}

	public void setGc(GameContainer gc) {
		this.gc = gc;
	}

	public HashMap<String, Entity> getEntities() {
		return entities;
	}

	public void setEntities(HashMap<String, Entity> entities) {
		this.entities = entities;
	}
}
