package com.uplinfo.readbook;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.martian.libsliding.SlidingLayout;
import com.martian.libsliding.slider.PageSlider;
import com.uplinfo.readbook.bean.Book;
import com.uplinfo.readbook.bean.Chapter;
import com.uplinfo.readbook.bean.PageParam;
import com.uplinfo.readbook.common.BookDao;
import com.uplinfo.readbook.view.adapter.ChapterListAdapter;
import com.uplinfo.readbook.view.adapter.PageViewSlidingAdapter;

public class SlidingChapterActivity extends Activity {

	private final static String TAG = "SlidingChapterActivity";

	private SlidingLayout slidingLayout;

	private View titleBar; // tool bar
	private View statusBar; // footer bar
	//
	private TextView txtTitle; // title
	private TextView tvBookReadToc; // 章节目录按钮
	private List<Chapter> chapters;

	private PopupWindow popupWindow; // 章节目录窗口
	private View popupWindowView; //
	private ListView chapterTitleView;
	//
	private int bookid;

	private BookDao bookDao;

	private Book book;

	private PageParam param;
	private PageViewSlidingAdapter pageAdapter;

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
		chapters = bookDao.queryChapterTitles(bookid);

		slidingLayout.post(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG, "--post--width:" + slidingLayout.getWidth());
				Log.d(TAG, "--post--height:" + slidingLayout.getHeight());
				param.setHeight(slidingLayout.getHeight());
				param.setWidth(slidingLayout.getWidth());
				initSlidingLayout();
				initView();
				// 章节名称列表
				initChapterTitle();
			}
		});

	}

	private void initSlidingLayout() {

		pageAdapter = new PageViewSlidingAdapter(this, book, param, bookDao);

		slidingLayout.setAdapter(pageAdapter);
		slidingLayout.setSlider(new PageSlider());

		slidingLayout.setOnTapListener(new SlidingLayout.OnTapListener() {

			@Override
			public void onSingleTap(MotionEvent event) {
				int screenWidth = getResources().getDisplayMetrics().widthPixels;

				int x = (int) event.getX();
				Log.d(TAG, "Tap X:" + x);
				Log.d(TAG, "screenWidth:" + screenWidth);
				int m = screenWidth / 6;
				if (x > (screenWidth / 2 + m)) {
					slidingLayout.slideNext();
				} else if (x <= (screenWidth / 2 - m)) {
					slidingLayout.slidePrevious();
				} else {
					Log.d(TAG, "center click: x= " + x);
					//
					if (titleBar.getVisibility() == View.VISIBLE) {
						titleBar.setVisibility(View.INVISIBLE);
						statusBar.setVisibility(View.INVISIBLE);
					} else {
						titleBar.setVisibility(View.VISIBLE);
						statusBar.setVisibility(View.VISIBLE);
					}
				}
			}
		});

	}

	void initView() {
		statusBar = (View) findViewById(R.id.chapter_status_bar);
		titleBar = (View) findViewById(R.id.tool_bar);
		txtTitle = (TextView) findViewById(R.id.txt_title);
		tvBookReadToc = (TextView) findViewById(R.id.tvBookReadToc);
		txtTitle.setText(book.getName());

		tvBookReadToc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWin(v);
			}

		});
	}

	private PageParam initPageParam() {
		PageParam p = new PageParam();
		
		AssetManager mgr = getAssets();
		Typeface tf = Typeface.createFromAsset(mgr, "fonts/xy.ttf");
			
		p.setFontColor(getResources().getColor(R.color.chapter_content_day));
		p.setBackColor(getResources().getColor(R.color.read_theme_green));
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(36F);
		//paint.setColor(Color.BLACK);
		paint.setColor(p.getFontColor());
		
		paint.setTypeface(tf);
		p.setPaint(paint);

		Paint titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		titlePaint.setTextSize(25F);
		//titlePaint.setColor(Color.BLACK);
		titlePaint.setColor(p.getFontColor());
		titlePaint.setTypeface(tf);
		p.setTitlePaint(titlePaint);

		p.setLineSpace(12);
		return p;
	}

	void initChapterTitle() {
		popupWindowView = getLayoutInflater().inflate(
				R.layout.activity_chapter_list, null, false);

		popupWindowView.setFocusable(true);
		popupWindowView.setFocusableInTouchMode(true);

		popupWindowView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				if (arg1 == KeyEvent.KEYCODE_BACK) {
					if (popupWindow != null) {
						popupWindow.dismiss();
						popupWindow = null;
					}
				}
				return false;
			}
		});

		chapterTitleView = (ListView) popupWindowView
				.findViewById(R.id.listview);

		View tool_bar = (View) popupWindowView.findViewById(R.id.tool_bar);
		tool_bar.setVisibility(View.GONE);

		chapterTitleView.setAdapter(new ChapterListAdapter(this, chapters));

		chapterTitleView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				pageAdapter.gotoPage((int) id);
				pageAdapter.notifyDataSetChanged();

				popupWindow.dismiss();
				popupWindow = null;
				titleBar.setVisibility(View.INVISIBLE);
				statusBar.setVisibility(View.INVISIBLE);
			}

		});
	}

	private void popWin(View v) {

		popupWindow = new PopupWindow(popupWindowView, param.getWidth(),
				param.getHeight(), true);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		popupWindow.setBackgroundDrawable(dw);
		popupWindow.showAtLocation(v, Gravity.CENTER, 0, 20);

	}

}
