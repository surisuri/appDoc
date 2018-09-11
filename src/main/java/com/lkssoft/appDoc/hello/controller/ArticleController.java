package com.lkssoft.appDoc.hello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	private ArticleService helloService;	
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @GetMapping("/")
	public String index() {
    	logger.trace("trace");
    	logger.debug("debug");
    	logger.info("info");
    	logger.warn("warn");
    	logger.error("error");
    	
		return "index";
	}
	
	@GetMapping("{articleId}")
	@ResponseBody
	public Article viewDetail(@PathVariable String articleId) {

    	logger.debug("debug"+articleId);

    	Article article = this.helloService.viewArticleDetail(articleId);
		return article;
	}
	
	@PostMapping("/write")
	@ResponseBody
	public Article write(@RequestBody Article article) {
		Article a = this.helloService.write(article);
		return a;
	}
}