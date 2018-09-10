package com.lkssoft.appDoc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkssoft.appDoc.hello.dao.ArticleDAO;
import com.lkssoft.appDoc.hello.vo.Article;

@Service
public class ArticleService {

	@Autowired
	private ArticleDAO articleDAO;

	public Article viewArticleDetail(String articleId) {
		return articleDAO.selectArticleById(articleId);
	}
	
	public Article write(Article article) {
		articleDAO.insertArticle(article);
		return article;
	}
}