package com.uplinfo.readbook.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class ViewUtil {

	static DateFormat format;
	
	static{
		format = new SimpleDateFormat("HH:mm");
	}
	public static void drawBattery(Canvas canvas, int left, int top,String t) {
		Paint batteryPaint = new Paint();

		float batteryStoke = 3F;
		float batteryHeight = 22F;
		float batteryWidth = 40F;
		float capHeight = 12F;
		float capWidth = 4F;

		RectF batteryRectF, capRectF;

		// ��ػ���
		batteryPaint.setColor(Color.GRAY);
		batteryPaint.setStrokeWidth(batteryStoke);
		batteryPaint.setStyle(Style.STROKE);
		batteryPaint.setAntiAlias(true);

		// ��������
		Paint powerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		powerPaint.setColor(Color.BLACK);
		powerPaint.setStrokeWidth(batteryStoke);
		powerPaint.setStyle(Style.FILL);
		powerPaint.setAntiAlias(true);
		powerPaint.setTextSize(16F);

		batteryRectF = new RectF(capWidth + left, top, left + capWidth
				+ batteryWidth, batteryHeight + top);

		capRectF = new RectF(left, top + batteryHeight / 2 - capHeight / 2,
				left + capWidth, batteryHeight / 2 + capHeight / 2 + top);

		canvas.drawRoundRect(batteryRectF, 2F, 2F, batteryPaint);
		canvas.drawRoundRect(capRectF, 1F, 1F, batteryPaint);

		// float measure = paint.measureText("100");

		canvas.drawText(t, capWidth + left + batteryWidth / 2 - 10, top
				+ batteryHeight / 2 + 6, powerPaint);

		Paint titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		titlePaint.setTextSize(25F);
		titlePaint.setColor(Color.BLACK);
		
		canvas.drawText(format.format(new Date()), capWidth + left
				+ batteryWidth + 10, top + batteryHeight / 2 + 8, titlePaint);
		
		
	}
	
	public static void drawRight(Canvas canvas, Paint t, String txt, int right, int top,int width) {
		float measure = t.measureText(txt);
		canvas.drawText(txt, width - (right + measure), top, t);
	}
}
