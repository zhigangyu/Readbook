package com.uplinfo.readbook.common;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;

public class StringUtils {

	/**
	 * 根据输入的文本信息分页
	 * 
	 * @param text
	 * @param width
	 * @param height
	 * @param lineSpace
	 * @param fontSpace
	 * @param paint
	 * @return
	 */
	private static List<String> calcText(String text, int width, int height,
			int lineSpace, int fontSpace, Paint paint) {
		FontMetrics fm = paint.getFontMetrics();
		int fontHeight = (int) Math.ceil(fm.descent - fm.ascent);
		int fontWeight = (int) paint.measureText("图");
		int lineHeight = fontHeight + lineSpace;

		StringBuilder buffer = new StringBuilder();
		List<String> pages = new ArrayList<String>();

		if (text != null) {
			if (!text.startsWith(" ")) {
				text = "　　" + text;
			}
			char[] chs = text.toCharArray();
			int x = 0;
			int y = 0;
			int line = 0;
			for (char c : chs) {
				if (x > width || c == '\n') {
					x = 0;
					y += lineHeight;
					line++;
				}
				if (y > height) {
					pages.add(buffer.toString());
					buffer.setLength(0);
					y = 0;
					line = 0;
				}

				buffer.append(c);

				if (checkSingle(c)) {
					x += paint.measureText(String.valueOf(c)) + fontSpace;
				} else {
					x += fontWeight + fontSpace;
				}
				
				if(c == '\n'){
					x += fontWeight + fontSpace;
				}
			}
			pages.add(buffer.toString());

		}
		return pages;
	}

	public static boolean checkSingle(char c) {

		if (c == '\r' || c == '\n') {
			return false;
		}

		byte[] b = String.valueOf(c).getBytes();

		if (b.length == 1)
			return true;
		return false;
	}

	public static void drawText(Canvas canvas, String txt, int width,
			int height, int top, int left, int lineSpace, int fontSpace,
			Paint paint, int marginHeight) {
		FontMetrics fm = paint.getFontMetrics();
		int fontHeight = (int) Math.ceil(fm.descent - fm.ascent);
		int fontWeight = (int) paint.measureText("图");
		int lineHeight = fontHeight + lineSpace;
		top = top + lineSpace;
		if (txt != null) {
			
			char[] chs = txt.toCharArray();
			int ww = left;
			int hh = top;

			for (char c : chs) {
				if (ww > (width - 2 * left - fontWeight/3 ) || c == '\n') {
					ww = left;
					hh += lineHeight;
				}
				
				if (hh > height ) {
					break;
				}

				canvas.drawText(String.valueOf(c), ww, hh, paint);

				if (checkSingle(c)) {
					ww += paint.measureText(String.valueOf(c));
				} else {
					ww += fontWeight + fontSpace;
				}
				
				if(c == '\n'){
					ww += fontWeight + fontSpace;
				}

			}

		}
	}

	public static List<String> calcText(String txt, int width, int height,
			int top, int left, int lineSpace, int fontSpace, Paint paint,
			int marginHeight) {
		FontMetrics fm = paint.getFontMetrics();
		int fontHeight = (int) Math.ceil(fm.descent - fm.ascent);
		int fontWeight = (int) paint.measureText("图");
		int lineHeight = fontHeight + lineSpace;

		top = top + lineSpace;
		
		StringBuilder buffer = new StringBuilder();
		List<String> pages = new ArrayList<String>();

		if (txt != null) {
			if (!txt.startsWith(" ")) {
				txt = "　　" + txt;
			}
			char[] chs = txt.toCharArray();
			int ww = left;
			int hh = top;

			for (char c : chs) {
				if (ww > (width - 2 * left- fontWeight/3) || c == '\n') {
					ww = left;
					hh += lineHeight;
				}
				if (hh > (height - top )) {
					pages.add(buffer.toString());
					buffer.setLength(0);
					hh = top;
				}

				buffer.append(c);
				
				if (checkSingle(c)) {
					ww += paint.measureText(String.valueOf(c));
				} else {
					ww += fontWeight + fontSpace;
				}
				
				if(c == '\n'){
					ww += fontWeight + fontSpace;
				}

			}
			pages.add(buffer.toString());
		}
		return pages;
	}

}
