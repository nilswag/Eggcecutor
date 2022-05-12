package com.nils.engine.gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.nils.engine.main.GameContainer;

public class ParticleHandler {
	
	private GameContainer gc;
	private ArrayList<Particle> particles;
	private ArrayList<Particle> particlesCopy;
	
	public ParticleHandler(GameContainer gc){
		this.gc = gc;
		particles = new ArrayList<Particle>();
		particlesCopy = new ArrayList<Particle>();
	}
	
	public void update(double dt) {
		for (Particle p : particles) {
			p.update(dt);
		}
	}
	
	public void render(Graphics g, Color color) {
		for (Particle p : particles) {
			p.render(g, color);
		}
	}
	
	public void generateParticles(float x, float y, int amount) {
		for (int i = 0; i < amount; i++) {
			particles.add(new Particle(x, y));
		}
	}

	public GameContainer getGc() {
		return gc;
	}

	public void setGc(GameContainer gc) {
		this.gc = gc;
	}

	public ArrayList<Particle> getParticles() {
		return particles;
	}

	public void setParticles(ArrayList<Particle> particles) {
		this.particles = particles;
	}

}
