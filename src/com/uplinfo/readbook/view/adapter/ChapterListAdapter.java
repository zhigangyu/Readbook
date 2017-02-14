package com.uplinfo.readbook.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uplinfo.readbook.R;
import com.uplinfo.readbook.bean.Chapter;

public class ChapterListAdapter extends BaseAdapter {
	
	private LayoutInflater inflater = null;
	private List<Chapter> chapters;

	public ChapterListAdapter(Context context,List<Chapter> chapters) {
		this.inflater = LayoutInflater.from(context);
		this.chapters = chapters;
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
			convertView = inflater.inflate(R.layout.item_chapter_title,
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
	
	final class ViewHolder {

		public TextView title;

	}

}
