package com.lkssoft.appDoc.hello.dao;

import org.springframework.stereotype.Repository;

import com.lkssoft.appDoc.hello.vo.Article;

@Repository
public class ArticleDAO {
	
	public Article selectArticleById(String articleId) {
		Article article = new Article(1, "lks", "venture", "rest spring test ¿‘¥œ¥Ÿ.");
		return article;
	}
}
