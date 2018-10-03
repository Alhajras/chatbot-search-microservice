package com.chatbot.springbootchatbot.elasticsearch.dao;


import com.chatbot.springbootchatbot.elasticsearch.jsons.Article;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Repository
public class ArticleDao {
    private static final Logger LOG = LoggerFactory.getLogger(ArticleDao.class);

    private final String INDEX = "articles";
    private final String TYPE = "article";

    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper;


    public ArticleDao(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
        this.objectMapper = objectMapper;
        this.restHighLevelClient = restHighLevelClient;
    }

    public Article insertArticle(Article article) {
        article.setId(UUID.randomUUID().toString());
        Map<String, Object> dataMap = objectMapper.convertValue(article, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, article.getId())
                .source(dataMap);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            e.getDetailedMessage();
        } catch (java.io.IOException ex) {
            ex.getLocalizedMessage();
        }
        return article;
    }

    public Map<String, Object> getArticleById(String id) {
        GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e) {
            e.getLocalizedMessage();
        }
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        return sourceAsMap;
    }

    public Map<String, Object> updateArticleById(String id, Article article) {
        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id)
                .fetchSource(true);    // Fetch Object after its update
        Map<String, Object> error = new HashMap<>();
        error.put("Error", "Unable to update article");
        try {
            String articleJson = objectMapper.writeValueAsString(article);
            updateRequest.doc(articleJson, XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            Map<String, Object> sourceAsMap = updateResponse.getGetResult().sourceAsMap();
            return sourceAsMap;
        } catch (JsonProcessingException e) {
            e.getMessage();
        } catch (java.io.IOException e) {
            e.getLocalizedMessage();
        }
        return error;
    }

    public void deleteArticleById(String id) {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e) {
            e.getLocalizedMessage();
        }
    }

    public void searchArticlesByAString(String searchWord) {

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("data", searchWord));
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse sr = restHighLevelClient.search(searchRequest , RequestOptions.DEFAULT);
            for(SearchHit hit : sr.getHits()){
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


}
