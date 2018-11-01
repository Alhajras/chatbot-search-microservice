package com.chatbot.springbootchatbot.controllers;
import com.chatbot.springbootchatbot.elasticsearch.dao.ArticleDao;
import com.chatbot.springbootchatbot.elasticsearch.jsons.Article;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private ArticleDao dao;

    public ArticleController(ArticleDao dao) {
        this.dao = dao;
    }

    @PostMapping
    public Article insertArticle(@RequestBody Article article) throws Exception{
        return dao.insertArticle(article);
    }

    @GetMapping ("/{id}")
    public Map<String, Object> getArticleById(@PathVariable String id){
        return dao.getArticleById(id);
    }

    @PutMapping ("/{id}")
    public Map<String, Object> updateArticleById(@RequestBody Article article, @PathVariable String id){
        return dao.updateArticleById(id, article);
    }

    @DeleteMapping("/{id}")
    public void deleteArticleById(@PathVariable String id){
        dao.deleteArticleById(id);
    }
}
