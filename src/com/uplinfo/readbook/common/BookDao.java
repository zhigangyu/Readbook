package com.uplinfo.readbook.common;

import java.util.List;

import android.database.Cursor;
import android.database.SQLException;

import com.uplinfo.readbook.bean.Book;
import com.uplinfo.readbook.bean.Chapter;

public class BookDao {

	private SqliteTemplate tpl;

	public BookDao() {
		tpl = new SqliteTemplate();
	}

	public Book queryBook(int id) {
		return tpl
				.queryForObject(
						"select id,name,author,cover,summary,chapterid,page from book where id=?",
						new String[] { String.valueOf(id) },
						new RowMapper<Book>() {

							@Override
							public Book mapRow(Cursor cursor, int index)
									throws SQLException {
								Book b = new Book();
								b.setId(cursor.getInt(0));
								b.setName(cursor.getString(1));
								b.setAuthor(cursor.getString(2));
								b.setCover(cursor.getString(3));
								b.setSummary(cursor.getString(4));
								b.setChapterid(cursor.getInt(5));
								b.setPage(cursor.getInt(6));
								return b;
							}

						});
	}

	/**
	 * 
	 * @return
	 */
	public List<Book> queryBooks() {
		return tpl.queryForList(
				"select id,name,author,cover,summary,chapterid,page from book",
				null, new RowMapper<Book>() {

					@Override
					public Book mapRow(Cursor cursor, int index)
							throws SQLException {
						Book b = new Book();
						b.setId(cursor.getInt(0));
						b.setName(cursor.getString(1));
						b.setAuthor(cursor.getString(2));
						b.setCover(cursor.getString(3));
						b.setSummary(cursor.getString(4));
						b.setChapterid(cursor.getInt(5));
						b.setPage(cursor.getInt(6));
						return b;
					}

				});
	}

	/**
	 * 
	 * @param id
	 * @param bookid
	 * @param p
	 * @return
	 */
	public Chapter queryChapter(int id, int bookid, int p) {
		String sql = "select id from content where bookid=? and ";
		if (p == -1) {
			sql += "id < ? order by id desc limit 0,1";
		} else {
			sql += "id > ? order by id asc limit 0,1";
		}

		Integer nextid = tpl.queryForInt(sql,
				new String[] { String.valueOf(bookid), String.valueOf(id) });

		if (nextid != null && nextid > 0)
			return queryChapter(nextid);
		return null;

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Chapter queryChapter(int id) {

		return tpl.queryForObject(
				"select id,bookid,title,content from content where id=?",
				new String[] { String.valueOf(id) },
				new RowMapper<Chapter>() {

					@Override
					public Chapter mapRow(Cursor cursor, int index)
							throws SQLException {
						Chapter book = new Chapter();
						book.setId(cursor.getInt(0));
						book.setBookid(cursor.getInt(1));
						book.setTitle(cursor.getString(2));
						book.setText(cursor.getString(3));
						return book;
					}

				});
	}

	/**
	 * 
	 * @param bookid
	 * @param chapterid
	 * @param page
	 */
	public void saveBookProgress(int bookid, int chapterid, int page) {

		tpl.exeSql("update book set chapterid=?,page=? where id=?",
				new Object[] { chapterid, page, bookid });

	}
	
	public List<Chapter> queryChapterTitles(int bookid){
		return tpl.queryForList(
				"select id,title from content where bookid=?",
				new String[] { String.valueOf(bookid) },
				new RowMapper<Chapter>() {

					@Override
					public Chapter mapRow(Cursor cursor, int index)
							throws SQLException {
						Chapter book = new Chapter();
						book.setId(cursor.getInt(0));
						book.setTitle(cursor.getString(1));
							return book;
					}

				});
	}

}
