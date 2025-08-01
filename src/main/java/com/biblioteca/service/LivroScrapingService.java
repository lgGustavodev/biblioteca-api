package com.biblioteca.service;

import com.biblioteca.dto.LivroDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

@Service
public class LivroScrapingService {

    private Random random = new Random();

    public LivroDTO extrairTituloAmazon(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10_000)
                .get();


        String titulo = doc.selectFirst("div.product_main > h1").text(); // título visível
        String precoStr = doc.selectFirst(".price_color").text().replace("£", "").replace(",", ".");
        BigDecimal preco = new BigDecimal(precoStr);


        String isbn = gerarIsbnAleatorio();
        Integer anoPublicacao = 2024;   

        return new LivroDTO(null, titulo, isbn, anoPublicacao, preco, null, null);
    }

    private String gerarIsbnAleatorio() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            int digito = random.nextInt(10);
            sb.append(digito);
        }
        return sb.toString();
    }

}
