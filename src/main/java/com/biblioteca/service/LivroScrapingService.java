package com.biblioteca.service;


import org.springframework.stereotype.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LivroScrapingService {

    public String extrairTituloAmazon(String url) throws IOException{
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10_000)
                .get();

        return doc.select("#productTitle").text();
    }
}
