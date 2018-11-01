package com.chatbot.springbootchatbot.controllers;

import com.chatbot.springbootchatbot.elasticsearch.dao.ArticleDao;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {


    private ArticleDao dao;

    public SearchController(ArticleDao dao) {
        this.dao = dao;
    }

    @RequestMapping("/search/{key}")
    public String search(@PathVariable String key){
        return dao.searchArticlesByAString(key);
    }

}
