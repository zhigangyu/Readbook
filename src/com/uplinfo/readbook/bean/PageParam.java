package com.uplinfo.readbook.bean;

import android.graphics.Paint;
import android.graphics.Typeface;

public class PageParam {
	private Paint paint, titlePaint;

	private int lineSpace = 2, fontSpace = 5;
	private int top = 100, margin = 15;
	private int fontColor, backColor;

	private Typeface typeface;
	
	public Typeface getTypeface() {
		return typeface;
	}

	public void setTypeface(Typeface typeface) {
		this.typeface = typeface;
	}

	public int getBackColor() {
		return backColor;
	}

	public int getFontColor() {
		return fontColor;
	}

	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}

	public void setBackColor(int backColor) {
		this.backColor = backColor;
	}

	private int width, height;

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

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public Paint getTitlePaint() {
		return titlePaint;
	}

	public void setTitlePaint(Paint titlePaint) {
		this.titlePaint = titlePaint;
	}

	public int getLineSpace() {
		return lineSpace;
	}

	public void setLineSpace(int lineSpace) {
		this.lineSpace = lineSpace;
	}

	public int getFontSpace() {
		return fontSpace;
	}

	public void setFontSpace(int fontSpace) {
		this.fontSpace = fontSpace;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}
}
