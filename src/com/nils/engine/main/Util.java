package com.nils.engine.main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.nils.engine.gfx.Display;

public class Util {
	
	public static void init() {
		try {
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/upheavtt.ttf")));
		} catch (IOException|FontFormatException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(Util.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage flip(BufferedImage img) {
		BufferedImage flipped = img;
		
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		
		tx.translate(-flipped.getWidth(null), 0);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		flipped = op.filter(flipped, null);
		
		return flipped;
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static BufferedImage getSubimage(BufferedImage sheet, int x, int y, int width, int height) {
		BufferedImage sub = null;
		
		sub = sheet.getSubimage(x*width, y*height, width, height);
		
		return sub;
	}
	
	public static void playMusic(String path) {
		Clip clip = null;
		FloatControl gainControl;
		
		try {
			InputStream audioSrc = Util.class.getResourceAsStream(path);
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
			baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2,
			baseFormat.getSampleRate(), false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			
			clip = AudioSystem.getClip();
			clip.open(dais);
			
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
			if (clip.isRunning()) {
				clip.stop();
			}
			
			clip.setFramePosition(0);
			while (!clip.isRunning()) {
				clip.start();
			}
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
}
