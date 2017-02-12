package com.uplinfo.readbook.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class SqliteTemplate {

	public final static String TAG = "com.uplinfo.book.SqliteTemplate";
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private SQLiteDatabase conect() throws IOException {
		File db = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/uplinbook");
		if (!db.exists()) {
			db.mkdir();
		}
		return SQLiteDatabase.openOrCreateDatabase(
				Environment.getExternalStorageDirectory().getPath()
						+ "/uplinbook/book.db", null);
	}
	public void exeSql(String sql) {
		SQLiteDatabase db = null;
		try {
			db = conect();
			db.execSQL(sql);
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
		} finally {
			try {
				if (null != db) {
					db.close();
				}
			} catch (Exception ex) {
				Log.e(TAG, ex.getMessage());
			}
		}
	}
	
	/**
	 * 
	 * @param sql
	 * @param bindArgs
	 */
	public void exeSql(String sql, Object[] bindArgs) {
		SQLiteDatabase db = null;
		try {
			db = conect();
			db.execSQL(sql, bindArgs);
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
		} finally {
			try {
				if (null != db) {
					db.close();
				}
			} catch (Exception ex) {
				Log.e(TAG, ex.getMessage());
			}
		}
	}
	/**
	 * 
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @return
	 */
	public <T> List<T> queryForList(String sql, String[] args,
			RowMapper<T> rowMapper) {
		SQLiteDatabase db = null;
		try {
			db = conect();
			Cursor cursor = db.rawQuery(sql, args);
			int i = 0;
			List<T> list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				list.add(rowMapper.mapRow(cursor, i++));
			}
			cursor.close();
			return list;
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
		} finally {
			try {
				if (null != db) {
					db.close();
				}
			} catch (Exception ex) {
				Log.e(TAG, ex.getMessage());
			}
		}
		return null;
	}
	/**
	 * 
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @return
	 */
	public <T> T queryForObject(String sql, String[] args,
			RowMapper<T> rowMapper) {
		List<T> list = queryForList(sql, args, rowMapper);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public Integer queryForInt(String sql, String[] args){
		Integer i = queryForObject(sql,args,new RowMapper<Integer>(){

			@Override
			public Integer mapRow(Cursor cursor, int index) throws SQLException {
				return cursor.getInt(0);
			}
			
		});
		return i;
	}
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public String queryForString(String sql, String[] args){
		String i = queryForObject(sql,args,new RowMapper<String>(){

			@Override
			public String mapRow(Cursor cursor, int index) throws SQLException {
				return cursor.getString(0);
			}
			
		});
		return i;
	}

}
