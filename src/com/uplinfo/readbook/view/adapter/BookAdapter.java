package com.uplinfo.readbook.view.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uplinfo.readbook.R;
import com.uplinfo.readbook.bean.Book;
import com.uplinfo.readbook.bean.Chapter;
import com.uplinfo.readbook.bean.PageParam;
import com.uplinfo.readbook.common.BookDao;
import com.uplinfo.readbook.common.ImageUtil;
import com.uplinfo.readbook.common.StringUtils;

public class BookAdapter extends BaseAdapter {

	private LayoutInflater mInflater = null;
	private List<Book> books;
	private PageParam param;
	private BookDao bookDao;

	public BookAdapter(Context context, List<Book> books, PageParam param,
			BookDao dao) {
		this.mInflater = LayoutInflater.from(context);
		this.books = books;
		this.param = param;
		bookDao = dao;
	}

	@Override
	public int getCount() {
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
			convertView = mInflater.inflate(R.layout.layout_item_book, null);
			holder.img = (ImageView) convertView.findViewById(R.id.img_photo);
			holder.title = (TextView) convertView.findViewById(R.id.txt_name);
			holder.info = (TextView) convertView.findViewById(R.id.txt_summary);
			
			TextPaint tp = holder.title.getPaint();
			tp.setFakeBoldText(true);

			if (param.getTypeface() != null) {
				holder.info.setTypeface(param.getTypeface());
				holder.title.setTypeface(param.getTypeface());
			}
			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Book b = books.get(position);
		holder.title.setText(b.getName());
		
		if (b.getSummary() != null) {
			holder.info.setText(b.getSummary());
		} 
		
		if (b.getCover() != null) {
			holder.img.setImageBitmap(ImageUtil
					.getBitmapFromPath(StringUtils.BOOK_ROOT + "/"
							+ b.getCover()));
			
		}
		
		if (b.getChapterid() > 0) {
			Chapter ch = bookDao.queryChapter(b.getChapterid());
			holder.info.setText(ch.getTitle());
		}
		return convertView;
	}

	// ViewHolder静态类
	final class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView info;
	}
}
