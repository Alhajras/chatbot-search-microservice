package com.chatbot.springbootchatbot.controllers;

import com.chatbot.springbootchatbot.elasticsearch.dao.ArticleDao;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {



    private ArticleDao dao;

    public GeneralController(ArticleDao dao) {
        this.dao = dao;
    }


    @RequestMapping("/hello")
    public String hello() {
        return " Hello !!";
    }

    @RequestMapping("/search/{key}")
    public String search(@PathVariable String key){
        return dao.searchArticlesByAString(key);
    }


}
