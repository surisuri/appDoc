package com.lkssoft.appDoc.hello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkssoft.appDoc.hello.service.ArticleService;
import com.lkssoft.appDoc.hello.vo.Article;

@Controller
@RequestMapping("/hello")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;	
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @GetMapping("/")
	public String index() {
		return "index";
	}
	
	/*@GetMapping("{articleId}")
	@ResponseBody
	public Article viewDetail(@PathVariable String articleId) {

    	Article article = this.articleService.viewArticleDetail(articleId);
		return article;
	}
	 */	
    
	@PostMapping("/write")
	@ResponseBody
	public Article write(@RequestBody Article article) {
		Article a = this.articleService.write(article);
		return a;
	}
}