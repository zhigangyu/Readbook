package com.uplinfo.readbook.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.uplinfo.readbook.R;
import com.uplinfo.readbook.bean.Chapter;
import com.uplinfo.readbook.common.StringUtils;
import com.uplinfo.readbook.common.TextEventListener;
import com.uplinfo.readbook.common.ViewUtil;

public class TextPageView extends View {

	private List<String> pages;
	private String text;
	private Paint paint, titlePaint;
	private int pageNum = 0;
	private TextEventListener textEventListener;
	private Chapter chapter;

	private int viewWidth;
	private int viewHeight;

	//
	private boolean end = false;
	private float startX = 0, nowX = 0;

	private int lineSpace = 2, fontSpace = 5;
	private int top = 100, margin = 15;

	public TextPageView(Context context) {
		super(context);
		init();
	}

	public TextPageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextPageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(38F);
		paint.setColor(Color.BLACK);

		titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		titlePaint.setTextSize(25F);
		titlePaint.setColor(Color.BLACK);

		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					startX = event.getX();
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					nowX = event.getX();
					postInvalidate();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {

					nowX = event.getX();
					if (Math.abs(nowX - startX) < 50F) {
						if (null != textEventListener) {
							textEventListener.onClick(chapter.getBookid(),
									chapter.getId(), pageNum);
						}
					} else if (nowX > startX) {
						// goto prev page
						if (pageNum > 0) {
							pageNum--;
							if (null != textEventListener) {
								textEventListener.onPageChange(
										chapter.getBookid(), chapter.getId(),
										pageNum);
							}
							text = pages.get(pageNum);
						} else {
							// go to prev paragraph last page
							if (null != textEventListener && chapter != null) {
								textEventListener.onParagraphPrev(
										chapter.getBookid(), chapter.getId());
								if (!end) {
									if (pages != null && pages.size() > 0) {
										pageNum = pages.size() - 1;
										text = pages.get(pageNum);
									}
								}
							}
						}
					} else {
						// goto next page
						if (pageNum < pages.size() - 1) {
							pageNum++;

							if (null != textEventListener) {
								textEventListener.onPageChange(
										chapter.getBookid(), chapter.getId(),
										pageNum);
							}
							text = pages.get(pageNum);
						} else {
							// go to next paragraph 1st page
							if (null != textEventListener && chapter != null) {
								textEventListener.onParagraphNext(
										chapter.getBookid(), chapter.getId());
								if (!end) {
									if (pages != null && pages.size() > 0) {
										pageNum = 0;
										text = pages.get(pageNum);
									}
								}
							}
						}

					}
					startX = 0;
					nowX = 0;
				}
				return false;
			}

		});

	}

	/**
	 * 根据父容器传递跟子容器的大小要求来确定子容器的大小
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int color = 0xffff9e85;
		//color = getResources().getColor(R.color.read_theme_yellow);
		canvas.drawColor(color);
		canvas.save();
		
		canvas.drawText(chapter.getTitle(), margin, 40, titlePaint);
		
		StringUtils.drawText(canvas, text, viewWidth, viewHeight, top, margin,
				lineSpace, fontSpace, paint, margin);
		ViewUtil.drawBattery(canvas, 15, viewHeight - 80, "70");

		ViewUtil.drawRight(canvas, titlePaint, (String.valueOf(pageNum + 1)
				+ "/" + pages.size()), margin + 5, viewHeight - 80 + 19,
				this.viewWidth);
		canvas.restore();
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		if (pageNum < pages.size()) {
			text = pages.get(pageNum);
			invalidate();
		}
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
		pages = StringUtils.calcText(chapter.getText(), viewWidth - margin * 2,
				viewHeight - top - margin * 2, lineSpace, fontSpace, paint);
		setPageNum(pageNum);
	}

	public int getViewWidth() {
		return viewWidth;
	}

	public void setViewWidth(int viewWidth) {
		this.viewWidth = viewWidth;
	}

	public int getViewHeight() {
		return viewHeight;
	}

	public void setViewHeight(int viewHeight) {
		this.viewHeight = viewHeight;
	}

	public TextEventListener getTextEventListener() {
		return textEventListener;
	}

	public void setTextEventListener(TextEventListener textEventListener) {
		this.textEventListener = textEventListener;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}
}
