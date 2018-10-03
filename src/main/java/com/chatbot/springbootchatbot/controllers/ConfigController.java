package com.chatbot.springbootchatbot.controllers;

import com.chatbot.springbootchatbot.elasticsearch.dao.ArticleDao;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {



    private ArticleDao dao;

    public ConfigController(ArticleDao dao) {
        this.dao = dao;
    }


    @RequestMapping("/hello")
    public String hello() {
        return " Hello !!";
    }

    @RequestMapping("/search/{key}")
    public String search(@PathVariable String key){
        dao.searchArticlesByAString(key);
        return "OK";
    }


}
