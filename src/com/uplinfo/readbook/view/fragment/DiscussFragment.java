package com.uplinfo.readbook.view.fragment;

import com.uplinfo.readbook.R;
import com.uplinfo.readbook.R.color;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DiscussFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView textView = new TextView(inflater.getContext());
		
		textView.setText("getResources().getText(R.string.book_detail_remove_collection)");
		
		textView.setTextColor(getResources().getColor(color.red));

		return textView;
	}
}
