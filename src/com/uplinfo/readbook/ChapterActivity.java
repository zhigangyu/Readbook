package com.uplinfo.readbook;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.uplinfo.readbook.bean.Book;
import com.uplinfo.readbook.bean.Chapter;
import com.uplinfo.readbook.common.BookDao;
import com.uplinfo.readbook.common.TextEventListener;
import com.uplinfo.readbook.view.TextPageView;

public class ChapterActivity extends Activity {

	private TextPageView txtView;
	private View titleBar, statusBar;
	Display display;
	//
	TextView txtTitle;
	TextView tvBookReadToc;
	//
	private int bookid, m_chapterid;

	private BookDao bookDao;

	private PopupWindow popupWindow;
	View popupWindowView;
	List<Chapter> chapters;
	ListView chapterTitleView;
	ListAdapter chapterTitleAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chapter);
		bookDao = new BookDao();

		statusBar = (View) findViewById(R.id.chapter_status_bar);
		titleBar = (View) findViewById(R.id.tool_bar);
		txtTitle = (TextView) findViewById(R.id.txt_title);
		tvBookReadToc = (TextView) findViewById(R.id.tvBookReadToc);
		tvBookReadToc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(ChapterActivity.this,
				// ChapterListActivity.class);
				// intent.putExtra("id", bookid);
				// startActivity(intent);
				popWin(v);
			}

		});
		readChapter();
		initChapterTitle();
	}

	void chaterChange(int bookid, int id, int p) {
		try {
			Chapter b = bookDao.queryChapter(id, bookid, p);
			if (b != null) {
				txtView.setChapter(b);
				txtView.setEnd(false);
			} else {
				txtView.setEnd(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void readChapter() {
		try {
			Intent _intent = getIntent();
			if (_intent != null) {
				bookid = (int) _intent.getIntExtra("id", 1);
			}

			Book book = bookDao.queryBook(bookid);
			txtTitle.setText(book.getName());

			// 章节名称列表
			chapters = bookDao.queryChapterTitles(bookid);

			Chapter b = bookDao.queryChapter(book.getChapterid());
			if (b == null) {
				b = bookDao.queryChapter(0, bookid, 1);
			}

			if (b != null) {
				txtView = (TextPageView) findViewById(R.id.txt_container);
				display = this.getWindowManager().getDefaultDisplay();
				txtView.setViewWidth(display.getWidth());
				txtView.setViewHeight(display.getHeight());

				txtView.setTextEventListener(new TextEventListener() {

					@Override
					public void onParagraphNext(int bookid, int id) {
						chaterChange(bookid, id, 1);
					}

					@Override
					public void onParagraphPrev(int bookid, int id) {
						chaterChange(bookid, id, -1);
					}

					@Override
					public void onPageChange(int bookid, int chapterid, int page) {

						m_chapterid = chapterid;
						bookDao.saveBookProgress(bookid, chapterid, page);
						if (titleBar.getVisibility() == View.VISIBLE) {
							titleBar.setVisibility(View.INVISIBLE);
							statusBar.setVisibility(View.INVISIBLE);
						}
					}

					@Override
					public void onClick(int bookId, int chapterId, int page) {
						if (titleBar.getVisibility() == View.VISIBLE) {
							titleBar.setVisibility(View.INVISIBLE);
							statusBar.setVisibility(View.INVISIBLE);
						} else {
							titleBar.setVisibility(View.VISIBLE);
							statusBar.setVisibility(View.VISIBLE);
						}
					}

				});

				txtView.setChapter(b);
				txtView.setPageNum(book.getPage());
				m_chapterid = book.getPage();

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

		chapterTitleAdapter = new ListAdapter(this);

		chapterTitleView = (ListView) popupWindowView
				.findViewById(R.id.listview);
		TextView poptitleView = (TextView) popupWindowView
				.findViewById(R.id.txt_title);
		View tool_bar = (View) popupWindowView.findViewById(R.id.tool_bar);
		tool_bar.setVisibility(View.GONE);

		chapterTitleView.setAdapter(chapterTitleAdapter);

		chapterTitleView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Chapter b = bookDao.queryChapter((int) id);
				txtView.setChapter(b);
				txtView.setPageNum(0);
				popupWindow.dismiss();
				popupWindow = null;
				titleBar.setVisibility(View.INVISIBLE);
				statusBar.setVisibility(View.INVISIBLE);
			}

		});
	}

	private void popWin(View v) {
		int w = (int) ((float) display.getWidth() * 1F);
		int h = (int) ((float) display.getHeight() * 0.98F);

		popupWindow = new PopupWindow(popupWindowView, w, h, true);
		// popupWindow.setAnimationStyle(R.style.AnimationFade);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		popupWindow.setBackgroundDrawable(dw);
		popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

		// popupWindow.showAsDropDown(v);
	}

	private void showError(String msg) {
		new AlertDialog.Builder(ChapterActivity.this).setTitle("Error")
				.setMessage(msg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
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

			holder.title.setText(chapters.get(position).getTitle());

			return convertView;
		}

	}

	final class ViewHolder {

		public TextView title;

	}

}
