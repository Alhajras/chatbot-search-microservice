package com.chatbot.springbootchatbot.parser;


import java.io.IOException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebPagesParser {
    private static final Logger LOG = LoggerFactory.getLogger(WebPagesParser.class);

    public void parseURL(String url){
        try {
            Document doc = Jsoup.connect(url).get();
            doc.children().forEach(child -> child.getElementsByTag("a href").forEach(element ->  LOG.info(element.toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
