package com.lkssoft.appDoc.hello.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lkssoft.appDoc.hello.vo.Article;

@Repository
public class ArticleDAO {

	@Autowired
	private SqlSession sqlSession;

	public Article selectArticleById(String articleId) {
		Article article = sqlSession.selectOne("mappers.article-mapper.selectArticleById", articleId);
		return article;
	}

	public void insertArticle(Article article) {
		sqlSession.insert("mappers.article-mapper.insertArticle", article);
	}
}