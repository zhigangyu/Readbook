package com.uplinfo.readbook.common;

import android.database.Cursor;
import android.database.SQLException;

public interface RowMapper<T> {
	T mapRow(Cursor cursor,int index)  throws SQLException;
}
