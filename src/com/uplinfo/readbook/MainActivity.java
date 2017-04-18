package com.uplinfo.readbook;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.uplinfo.readbook.bean.Book;
import com.uplinfo.readbook.bean.PageParam;
import com.uplinfo.readbook.common.BookDao;
import com.uplinfo.readbook.view.adapter.BookAdapter;

public class MainActivity extends Activity {

	private List<Book> books;
	private BookDao bookDao;
	private ListView listView;
	private BookAdapter bookAdapter;
	private SwipeRefreshLayout swipeRefreshLayout;

	private BookApplication appState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		appState = ((BookApplication) getApplicationContext());

		AssetManager mgr = getAssets();
		Typeface tf = Typeface.createFromAsset(mgr, "fonts/xy.ttf");

		if (appState.getParam() == null) {
			PageParam p = new PageParam();

			p.setTypeface(tf);

			p.setFontColor(getResources().getColor(R.color.chapter_content_day));
			//p.setBackColor(getResources().getColor(R.color.read_theme_yellow));
			p.setBackColor(0xffff9e85);

			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setTextSize(dip2px(this,18F));
			paint.setColor(p.getFontColor());

			paint.setTypeface(tf);
			p.setPaint(paint);

			Paint titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			titlePaint.setTextSize(dip2px(this,12F));
			titlePaint.setColor(p.getFontColor());
			titlePaint.setTypeface(tf);
			p.setTitlePaint(titlePaint);

			p.setLineSpace(18);
			p.setTop(156);
			
			p.setMargin(40);
			p.setStatusBarHeight(statusBarHeight(getResources()));
			
			/*荣耀6 分辨率*/
//			paint.setTextSize(36F); 
//			titlePaint.setTextSize(25F);
//			p.setLineSpace(12);
//			p.setTop(100);
//			p.setMargin(15);
//			p.setStatusBarHeight(40);
			
			appState.setParam(p);
		}

		TextView tvTitle = (TextView) findViewById(R.id.txt_title);

		tvTitle.setTypeface(tf);
		
		init();
	}
	
	private int statusBarHeight(android.content.res.Resources res) {
	    return (int) (24 * res.getDisplayMetrics().density);
	}

	private void init() {
		bookDao = new BookDao();
		books = bookDao.queryBooks();
		listView = (ListView) findViewById(R.id.listview);
		
		bookAdapter = new BookAdapter(this,books,appState.getParam(),bookDao);
		
		listView.setAdapter(bookAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(MainActivity.this,
						SlidingChapterActivity.class);
				intent.putExtra("id", (int) id);
				startActivity(intent);

			}

		});

		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);

		// ★1.设置刷新时动画的颜色，可以设置4个
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_light,
				android.R.color.holo_red_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light);

		// ★2.设置刷新监听事件
		swipeRefreshLayout
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						books = bookDao.queryBooks();

						handler.sendEmptyMessage(1);
					}
				});

		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				boolean enable = false;
				if (listView != null && listView.getChildCount() > 0) {
					// check if the first item of the list is visible
					boolean firstItemVisible = listView
							.getFirstVisiblePosition() == 0;
					// check if the top of the first item is visible
					boolean topOfFirstItemVisible = listView.getChildAt(0)
							.getTop() == 0;
					// enabling or disabling the refresh layout
					enable = firstItemVisible && topOfFirstItemVisible;
				}
				swipeRefreshLayout.setEnabled(enable);
			}
		});

		// swipeRefreshLayout.post(new Runnable() {
		// @Override
		// public void run() {
		// swipeRefreshLayout.setRefreshing(true);
		// }
		// });

	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			bookAdapter.notifyDataSetChanged();
			swipeRefreshLayout.setRefreshing(false);
		}
	};

	/** 
	* 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
	*/  
	public static int dip2px(Context context, float dpValue) {  
	  final float scale = context.getResources().getDisplayMetrics().density;  
	  return (int) (dpValue * scale + 0.5f);  
	}  
	  
	/** 
	* 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
	*/  
	public static int px2dip(Context context, float pxValue) {  
	  final float scale = context.getResources().getDisplayMetrics().density;  
	  return (int) (pxValue / scale + 0.5f);  
	}
	
	
	
}
