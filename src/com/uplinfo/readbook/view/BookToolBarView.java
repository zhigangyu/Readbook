package com.uplinfo.readbook.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.uplinfo.readbook.R;

public class BookToolBarView extends FrameLayout {

	private TextView txt_left,txt_title,txt_right;
	private ImageView img_right,img_back;

	public BookToolBarView(Context context) {
		super(context);
		init(context);
	}

	public BookToolBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public BookToolBarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		View contextView = LayoutInflater.from(context).inflate(
				R.layout.layout_title,this);
		
		txt_left = (TextView) contextView.findViewById(R.id.txt_left);
		txt_title = (TextView) contextView.findViewById(R.id.txt_title);
		txt_right = (TextView) contextView.findViewById(R.id.txt_right);
		
		img_right = (ImageView) contextView.findViewById(R.id.img_right);		
		img_back = (ImageView) contextView.findViewById(R.id.img_back);
		
		
	}

}
