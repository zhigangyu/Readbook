package com.uplinfo.readbook.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;

import com.uplinfo.readbook.R;
import com.uplinfo.readbook.common.ImageUtil;

public class BookCoverView extends View {

	private Rect mSrcRect, mDestRect;
	
	private Paint mBitPaint;  
    private Bitmap mBitmap; 
    
    private int mTotalWidth, mTotalHeight;  

	public BookCoverView(Context context) {
		super(context);
		initPaint();
	}

	public BookCoverView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	public BookCoverView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PageView,defStyleAttr, 0);
		initPaint();
	}

	private void initPaint() {
		mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBitPaint.setFilterBitmap(true);
		mBitPaint.setDither(true);
		
	}
	
	@Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
    }  
  
    @Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
        mTotalWidth = w;  
        mTotalHeight = h;  
        
    } 

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(mBitmap != null){
			canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mBitPaint); 
		}
	}
	
	public void setImage(String p){
		try{
			String img = Environment.getExternalStorageDirectory().getPath()
					+ "/uplinbook/" + p;
			Bitmap photoBitmap = BitmapFactory.decodeFile(img);
			
			//this.mBitmap =ImageUtil.convertToBlackWhite(photoBitmap);
			this.mBitmap = ImageUtil.zoomBitmap(photoBitmap,200,200);
			mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());  
			mDestRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());  
			this.invalidate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
