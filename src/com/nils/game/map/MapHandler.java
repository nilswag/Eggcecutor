package com.nils.game.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.nils.engine.main.GameContainer;
import com.nils.engine.main.Util;

public class MapHandler {

	private GameContainer gc;
	private HashMap<Integer, BufferedImage> imgDir;
	private int[][] map = generateMap();
	
	public MapHandler(GameContainer gc) {
		this.gc = gc;
		imgDir = new HashMap<Integer, BufferedImage>();
		
		imgDir.put(1, Util.loadImage("/sand-tile.png"));
		imgDir.put(2, Util.loadImage("/sand-tile2.png"));
	}
	
	public void update(double dt) {}
	
	public void render(Graphics g) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] != 0) {
					g.drawImage(imgDir.get(map[y][x]), x*32, y*32, null);
				}
			}
		}
	}
	
	private int[][] generateMap(){
		int[][] map = new int[7][10];
		
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				map[y][x] = 1;
				if (Util.randInt(0, 100) < 25) {map[y][x] = 2;}
			}
		}
		
		return map;
	}

	public GameContainer getGc() {
		return gc;
	}

	public void setGc(GameContainer gc) {
		this.gc = gc;
	}

	public HashMap<Integer, BufferedImage> getImgDir() {
		return imgDir;
	}

	public void setImgDir(HashMap<Integer, BufferedImage> imgDir) {
		this.imgDir = imgDir;
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}
	
}
