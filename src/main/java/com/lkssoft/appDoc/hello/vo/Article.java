package com.lkssoft.appDoc.hello.vo;

public class Article {
	private int articleId;
	
	private String author;
	
	private String title;
	
	private String content;


	public Article(int articleId, String author, String title, String content) {
		this.articleId = articleId;
		this.author = author;
		this.title = title;
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
}
