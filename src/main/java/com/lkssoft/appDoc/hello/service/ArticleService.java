package com.lkssoft.appDoc.hello.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkssoft.appDoc.hello.dao.ArticleDAO;
import com.lkssoft.appDoc.hello.vo.Article;

@Service
public class ArticleService {

	@Autowired
	private ArticleDAO articleDAO;

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    public Article viewArticleDetail(String articleId) {
    	
    	logger.debug("viewArticleDetail service");
    	
		return articleDAO.selectArticleById(articleId);
	}
	
	public Article write(Article article) {
		articleDAO.insertArticle(article);
		return article;
	}
}