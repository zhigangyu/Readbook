package com.uplinfo.readbook.view.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.martian.libsliding.SlidingAdapter;
import com.uplinfo.readbook.R;
import com.uplinfo.readbook.bean.Book;
import com.uplinfo.readbook.bean.BookPage;
import com.uplinfo.readbook.bean.Chapter;
import com.uplinfo.readbook.bean.PageParam;
import com.uplinfo.readbook.common.BookDao;
import com.uplinfo.readbook.common.ViewUtil;
import com.uplinfo.readbook.view.PageView;

public class PageViewSlidingAdapter extends SlidingAdapter<BookPage> {

	private final static String TAG = "PageViewSlidingAdapter";
	private Context context;
	private int index;
	private int chapterId;

	private BookDao bookDao;

	private Book book;

	private List<BookPage> pages;

	private PageParam param;

	public PageViewSlidingAdapter(Context context, Book book, PageParam param,
			BookDao bookDao) {
		this.context = context;
		this.book = book;
		this.param = param;
		this.bookDao = bookDao;

		Chapter chapter = bookDao.queryChapter(book.getChapterid());
		if (chapter == null) {
			chapter = bookDao.queryChapter(0, book.getId(), 1);
		}
		// caculate pages;
		pages = ViewUtil.calcBookPage(chapter, param);
		index = book.getPage()-1;

	}

	@Override
	protected void computeNext() {
		++index;
	}

	@Override
	protected void computePrevious() {
		--index;
	}

	@Override
	public BookPage getCurrent() {
		BookPage b;
		b = pages.get(index);
		chapterId = b.getId();
		Log.d(TAG,
				"getCurrent index,pageNum, page.size:" + index + ", " + b.getPageNum() +"," + pages.size());
		// save prosess
		bookDao.saveBookProgress(b.getBookid(), b.getId(), b.getPageNum());
		return b;
	}

	@Override
	public BookPage getNext() {
		return pages.get(index + 1);
	}

	@Override
	public BookPage getPrevious() {
		return pages.get(index - 1);
	}

	@Override
	public View getView(View contentView, BookPage bookPage) {
		if (contentView == null)
			contentView = LayoutInflater.from(context).inflate(
					R.layout.item_flipper_chapter, null);

		PageView pageView = (PageView) contentView.findViewById(R.id.textview);
		pageView.setParam(param);
		pageView.setText(bookPage.getText());
		pageView.setFooter(bookPage.getFooter());
		pageView.setTitle(bookPage.getTitle());

		return contentView;
	}

	@Override
	public boolean hasNext() {
		if (index > (pages.size() - 2)) {
			Chapter b = bookDao.queryChapter(chapterId, book.getId(), 1);
			if (b == null) {
				return false;
			}
			//
			List<BookPage> nextpages = ViewUtil.calcBookPage(b, param);
			pages.addAll(nextpages);
		}
		return true;
	}

	@Override
	public boolean hasPrevious() {
		if (index < 1) {
			Chapter b = bookDao.queryChapter(chapterId, book.getId(), -1);
			if (b == null) {
				return false;
			}
			//
			List<BookPage> nextpages = ViewUtil.calcBookPage(b, param);
			index += nextpages.size();
			pages.addAll(0,nextpages);
		}
		return true;
	}

}
