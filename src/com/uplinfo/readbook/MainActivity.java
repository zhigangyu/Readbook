package com.uplinfo.readbook;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.uplinfo.readbook.bean.Book;
import com.uplinfo.readbook.bean.Chapter;
import com.uplinfo.readbook.common.BookDao;

public class MainActivity extends Activity {

	private List<Book> books;
	private BookDao bookDao;
	private ListView listView;
	private BookAdapter bookAdapter;
	private SwipeRefreshLayout swipeRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		bookDao = new BookDao();
		books = bookDao.queryBooks();
		listView = (ListView) findViewById(R.id.listview);
		bookAdapter = new BookAdapter(this);
		listView.setAdapter(bookAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

//				 Intent intent = new Intent(MainActivity.this,
//				 ChapterActivity.class);
//				 intent.putExtra("id", (int) id);
//				 startActivity(intent);
				Intent intent = new Intent(MainActivity.this,	SlidingChapterActivity.class);
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

	// ViewHolder静态类
	final class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView info;
	}

	class BookAdapter extends BaseAdapter {

		private LayoutInflater mInflater = null;

		private BookAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return books.size();
		}

		@Override
		public Object getItem(int position) {
			return books.get(position);
		}

		@Override
		public long getItemId(int position) {
			return books.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			// 如果缓存convertView为空，则需要创建View
			if (convertView == null) {
				holder = new ViewHolder();
				// 根据自定义的Item布局加载布局
				convertView = mInflater
						.inflate(R.layout.layout_item_book, null);
				holder.img = (ImageView) convertView
						.findViewById(R.id.img_photo);
				holder.title = (TextView) convertView
						.findViewById(R.id.txt_name);
				holder.info = (TextView) convertView
						.findViewById(R.id.txt_summary);
				// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Book b = books.get(position);
			holder.title.setText(b.getName());
			if (b.getChapterid() > 0) {
				Chapter ch = bookDao.queryChapter(b.getChapterid());
				holder.info.setText(ch.getTitle());
			} else {
				if (b.getSummary() != null) {
					holder.info.setText(b.getSummary());
				} else {
					holder.info.setText("");
				}
			}

			return convertView;
		}

	}
}
