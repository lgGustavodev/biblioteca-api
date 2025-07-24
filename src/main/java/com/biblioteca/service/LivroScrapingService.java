package com.biblioteca.service;

import com.biblioteca.dto.LivroDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class LivroScrapingService {

    public LivroDTO extrairTituloAmazon(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10_000)
                .get();

        // Usando o site books.toscrape.com como exemplo
        String titulo = doc.selectFirst("div.product_main > h1").text(); // título visível
        String precoStr = doc.selectFirst(".price_color").text().replace("£", "").replace(",", ".");
        BigDecimal preco = new BigDecimal(precoStr);

        // Simulações para campos que não existem no site
        String isbn = "1234567890123";              // Valor fictício válido (13 dígitos)
        Integer anoPublicacao = 2024;               // Ano fictício

        return new LivroDTO(null, titulo, isbn, anoPublicacao, preco, null, null);
    }

}
