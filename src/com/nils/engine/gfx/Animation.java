package com.nils.engine.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.nils.engine.main.Util;

public class Animation {
	
	private BufferedImage sheet;
	private ArrayList<BufferedImage> images;
	private int xImages, yImages;
	private int width, height;
	private double[] timers;
	private double scale;
	
	public Animation(BufferedImage sheet, int xImages, int yImages, int width, int height, double[] timers, double scale) {
		this.sheet = sheet;
		this.xImages = xImages;
		this.yImages = yImages;
		this.width = width;
		this.height = height;
		this.timers = timers;
		this.scale = scale;
		
		images = new ArrayList<BufferedImage>();
		for (int y = 0; y < yImages; y++) {
			for (int x = 0; x < xImages; x++) {
				BufferedImage subImage = sheet.getSubimage(x*width, y*height, width, height);
				images.add(subImage);
			}
		}
	}
	
	private double timer = 0;
	private int frameCount = 0;
	
	public void render(Graphics g, double dt, int x, int y, boolean flip) {
		timer += dt;
		
		BufferedImage currentFrame = images.get(frameCount);
		double realTime = 0;
		
		realTime = timers[frameCount];
		
		if (timer >= realTime) {
			frameCount++;
			timer = 0;
		}
		
		if (frameCount >= images.size()) {
			frameCount = 0;
		}
		
		BufferedImage flipped = currentFrame;
		
		if (flip) {
			flipped = Util.flip(currentFrame);
		}
		
		g.drawImage(flipped, x, y, (int) (width*scale), (int) (height*scale), null);
	}

	public BufferedImage getSheet() {
		return sheet;
	}

	public void setSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}

	public ArrayList<BufferedImage> getImages() {
		return images;
	}

	public void setImages(ArrayList<BufferedImage> images) {
		this.images = images;
	}

	public int getxImages() {
		return xImages;
	}

	public void setxImages(int xImages) {
		this.xImages = xImages;
	}

	public int getyImages() {
		return yImages;
	}

	public void setyImages(int yImages) {
		this.yImages = yImages;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double[] getTimers() {
		return timers;
	}

	public void setTimers(double[] timers) {
		this.timers = timers;
	}

	public double getTimer() {
		return timer;
	}

	public void setTimer(double timer) {
		this.timer = timer;
	}

	public int getFrameCount() {
		return frameCount;
	}

	public void setFrameCount(int frameCount) {
		this.frameCount = frameCount;
	}

}
