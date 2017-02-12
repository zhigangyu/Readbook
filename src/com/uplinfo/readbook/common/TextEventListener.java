package com.uplinfo.readbook.common;

public interface TextEventListener {
	void onPageChange(int bookId, int chapterId, int page);

	void onParagraphNext(int bookid, int chapterId);

	void onParagraphPrev(int bookid, int chapterId);

	void onClick(int bookId, int chapterId, int page);
}
