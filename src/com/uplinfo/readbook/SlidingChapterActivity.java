package com.uplinfo.readbook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.martian.libsliding.SlidingLayout;
import com.martian.libsliding.slider.PageSlider;
import com.uplinfo.readbook.bean.Book;
import com.uplinfo.readbook.bean.PageParam;
import com.uplinfo.readbook.common.BookDao;
import com.uplinfo.readbook.view.TextPageView;
import com.uplinfo.readbook.view.adapter.PageViewSlidingAdapter;

public class SlidingChapterActivity extends Activity {
	
	private final static String TAG = "SlidingChapterActivity";

	private SlidingLayout slidingLayout;

	private TextPageView txtView;
	private View titleBar, statusBar;
	//
	private TextView txtTitle;
	private TextView tvBookReadToc;
	//
	private int bookid;

	private BookDao bookDao;

	private Book book;

	private PageParam param;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sliding_chapter);

		slidingLayout = (SlidingLayout) findViewById(R.id.sliding_container);

		Intent _intent = getIntent();
		if (_intent != null) {
			bookid = (int) _intent.getIntExtra("id", 1);
		}

		param = initPageParam();

		bookDao = new BookDao();

		book = bookDao.queryBook(bookid);

		slidingLayout.post(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG, "--post--width:" + slidingLayout.getWidth());
				Log.d(TAG, "--post--height:" + slidingLayout.getHeight());
				param.setHeight(slidingLayout.getHeight());
				param.setWidth(slidingLayout.getWidth());
				init();
			}
		});

	}

	private void init() {

		
		PageViewSlidingAdapter adapter = new PageViewSlidingAdapter(this, book,
				param, bookDao);

		slidingLayout.setAdapter(adapter);
		slidingLayout.setSlider(new PageSlider());

		slidingLayout.setOnTapListener(new SlidingLayout.OnTapListener() {

			@Override
			public void onSingleTap(MotionEvent event) {
				int screenWidth = getResources().getDisplayMetrics().widthPixels;
				
				int x = (int) event.getX();
				Log.d(TAG, "Tap X:" + x);
				Log.d(TAG, "screenWidth:" + screenWidth);
				int m = screenWidth/6;
				if (x > (screenWidth / 2 + m)) {
					slidingLayout.slideNext();
				} else if (x <= (screenWidth / 2-m)) {
					slidingLayout.slidePrevious();
				}else{
					Log.d(TAG, "center click: x= " + x);
				}
			}
		});

	}

	private PageParam initPageParam() {
		PageParam p = new PageParam();
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(38F);
		paint.setColor(Color.BLACK);
		p.setPaint(paint);

		Paint titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		titlePaint.setTextSize(25F);
		titlePaint.setColor(Color.BLACK);
		p.setTitlePaint(titlePaint);

		return p;
	}

}
