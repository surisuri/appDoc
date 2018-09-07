package com.lkssoft.appDoc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkssoft.appDoc.hello.dao.ArticleDAO;
import com.lkssoft.appDoc.hello.vo.Article;

@Service
public class HelloService {

	@Autowired
	private ArticleDAO articleDAO;

	public Article viewArticleDetail(String articleId) {
		return articleDAO.selectArticleById(articleId);
	}
	
}
