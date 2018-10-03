package com.chatbot.springbootchatbot.elasticsearch.indices;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * The Package com.chatbot.springbootchatbot.elasticsearch.indices will be
 */
public class ArticlesIndex {

    private final String ARTICLESINDEX = "articles";

    public String createIndex(RestHighLevelClient client) {
        GetIndexRequest getIndexRequest = new GetIndexRequest().indices(ARTICLESINDEX);

        try {
            boolean getIndexResponse = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);

            if (getIndexResponse) {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        CreateIndexRequest request = new CreateIndexRequest(ARTICLESINDEX);
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (createIndexResponse.isAcknowledged())
            return "";

        return "Error Creating Index " + ARTICLESINDEX;
    }

}
