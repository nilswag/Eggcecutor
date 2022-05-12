package com.nils.engine.state;

import java.awt.Graphics;
import java.util.HashMap;

import com.nils.engine.main.GameContainer;

public class StateHandler {
	
	private GameContainer gc;
	private String currentState;
	private HashMap<String, State> states;
	
	public StateHandler(GameContainer gc) {
		this.gc = gc;
		states = new HashMap<String, State>();
		currentState = "";
	}
	
	public void update(double dt) {
		if (currentState != "") {
			states.get(currentState).update(dt);
		}
	}
	
	public void render(Graphics g) {
		if (currentState != "") {
			states.get(currentState).render(g);
		}
	}
	
	public void setState(State state) {
		states.put(state.getTag(), state);
	}
	
	public State getState(String tag) {
		return states.get(tag);
	}

	public GameContainer getGc() {
		return gc;
	}

	public void setGc(GameContainer gc) {
		this.gc = gc;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public HashMap<String, State> getStates() {
		return states;
	}

	public void setStates(HashMap<String, State> states) {
		this.states = states;
	}

}
