package com.uplinfo.readbook.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.uplinfo.readbook.bean.PageParam;
import com.uplinfo.readbook.common.StringUtils;
import com.uplinfo.readbook.common.ViewUtil;

public class PageView extends View {

	private String text;
	private String title, footer;

	private int viewWidth;
	private int viewHeight;

	private PageParam param;

	public PageView(Context context) {
		super(context);
	}

	public PageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		viewWidth = getMeasuredWidth();
		viewHeight = getMeasuredHeight();
		Log.d("PageView", "--PageView--width:" + viewWidth);
		Log.d("PageView", "--PageView--height:" + viewHeight);
	}


	/**
	 * 根据父容器传递跟子容器的大小要求来确定子容器的大小
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int color = 0xffff9e85;
		canvas.drawColor(param.getBackColor());

		if (param == null) {
			return;
		}
		canvas.save();

		if (title != null) {
			canvas.drawText(title, param.getMargin(), 60, param.getTitlePaint());
		}
		StringUtils.drawText(canvas, text, viewWidth, viewHeight,
				param.getTop(), param.getMargin(), param.getLineSpace(),
				param.getFontSpace(), param.getPaint(), param.getMargin());
		
		ViewUtil.drawBattery(canvas, 15, viewHeight - 40, "70",param.getTitlePaint());

		if (footer != null) {
			ViewUtil.drawRight(canvas, param.getTitlePaint(), footer,
					param.getMargin() + 5, viewHeight - 40 + 19, this.viewWidth);
		}
		canvas.restore();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		invalidate();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public PageParam getParam() {
		return param;
	}

	public void setParam(PageParam param) {
		this.param = param;
	}

}
