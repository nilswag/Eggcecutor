package com.nils.engine.gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nils.engine.main.Util;

public class Font {

	private BufferedImage fontImage;
	private int[] offsets;
	private int[] widths;
	
	public Font() {
		fontImage = Util.loadImage("/font.png");
		offsets = new int[59];
		widths = new int[59];
		
		int unicode = 0;
		
		for (int y = 0; y < fontImage.getHeight(); y++) {
			for (int x = 0; x < fontImage.getWidth(); x++) {
				if (fontImage.getRGB(x, y) == 0xff0000ff) {
					offsets[unicode] = x + y * fontImage.getWidth();
				}
				
				if (fontImage.getRGB(x, y) == 0xffffff00) {
					widths[unicode] = (x + y * fontImage.getWidth()) - offsets[unicode];
					unicode++;
				}
			}
		}
	}
	
	public void drawString(Graphics g, String str, int offX, int offY, int color) {
		str = str.toUpperCase();
		
		int offset = 0;
		
		for (int i = 0; i < str.length(); i++) {
			int unicode = str.codePointAt(i) - 32;
			
			for (int y = 0; y < fontImage.getHeight(); y++) {
				for (int x = 0; x < widths[unicode]; x++) {
					if (fontImage.getRGB(x + offsets[unicode], y) == 0xffffffff) {
						g.setColor(Color.decode(Integer.toString(color)));
						g.fillRect(x + offX + offset, y + offY, 1, 1);
					}
				}
			}
			
			offset += widths[unicode];
		}
	}

	public BufferedImage getFontImage() {
		return fontImage;
	}

	public void setFontImage(BufferedImage fontImage) {
		this.fontImage = fontImage;
	}

	public int[] getOffsets() {
		return offsets;
	}

	public void setOffsets(int[] offsets) {
		this.offsets = offsets;
	}

	public int[] getWidths() {
		return widths;
	}

	public void setWidths(int[] widths) {
		this.widths = widths;
	}
	
}
