package com.uplinfo.readbook;

import android.app.Application;

import com.uplinfo.readbook.bean.PageParam;

/**
 * 
 * @author Ray
 *
 */
public class BookApplication extends Application {
	private PageParam param;

	public PageParam getParam() {
		return param;
	}

	public void setParam(PageParam param) {
		this.param = param;
	}
}
