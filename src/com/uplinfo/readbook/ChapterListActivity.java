package com.uplinfo.readbook;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.uplinfo.readbook.bean.Book;
import com.uplinfo.readbook.bean.Chapter;
import com.uplinfo.readbook.common.BookDao;

public class ChapterListActivity extends Activity {

	BookDao dao;
	List<Chapter> chapters;
	int bookid;
	ListView listView;
	ListAdapter listAdapter;
	TextView txtTitle;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chapter_list);
		init();
	}

	private void init() {
		listView = (ListView) findViewById(R.id.listview);
		txtTitle = (TextView) findViewById(R.id.txt_title);
		
		dao = new BookDao();
		Intent _intent = getIntent();
		if (_intent != null) {
			bookid = _intent.getIntExtra("id", 1);
		}

		Book book = dao.queryBook(bookid);
		txtTitle.setText(book.getName());
		
		chapters = dao.queryChapterTitles(bookid);
		
		listAdapter = new ListAdapter(this);

		listView.setAdapter(listAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				save((int) id);
				Intent intent = new Intent(ChapterListActivity.this,
						ChapterActivity.class);
				intent.putExtra("id", bookid);
				startActivity(intent);

			}

		});
	}

	void save(int id) {
		dao.saveBookProgress(bookid, id, 0);
	}

	class ListAdapter extends BaseAdapter {
		private LayoutInflater mInflater = null;

		private ListAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return chapters.size();
		}

		@Override
		public Object getItem(int position) {
			return chapters.get(position);
		}

		@Override
		public long getItemId(int position) {
			return chapters.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			// 如果缓存convertView为空，则需要创建View
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_chapter_title,
						null);

				holder.title = (TextView) convertView
						.findViewById(R.id.txt_left);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.title
					.setText(chapters.get(position).getTitle());

			return convertView;
		}

	}

	final class ViewHolder {

		public TextView title;

	}
}
