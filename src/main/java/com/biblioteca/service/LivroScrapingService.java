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

        // 🔍 Título
        String titulo = "Título não encontrado";
        Element tituloElement = doc.selectFirst("#productTitle");
        if (tituloElement != null) {
            titulo = tituloElement.text().trim();
        }

        // 💰 Preço
        BigDecimal preco = BigDecimal.ZERO;
        Element precoElement = doc.selectFirst(".a-price-whole");
        if (precoElement != null && !precoElement.text().isBlank()) {
            String precoStr = precoElement.text().replace(".", "").replace(",", ".");
            try {
                preco = new BigDecimal(precoStr);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Erro ao converter o preço: " + precoStr);
            }
        }

        // 📆 Ano de publicação
        Integer anoPublicacao = 0;
        String detalhes = doc.select("#detailBullets_feature_div").text();
        if (detalhes != null && !detalhes.isBlank()) {
            String anoPublicacaoStr = detalhes.replaceAll(".*(\\d{4}).*", "$1");
            try {
                anoPublicacao = Integer.parseInt(anoPublicacaoStr);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Erro ao converter o ano de publicação: " + anoPublicacaoStr);
            }
        }

        // 📖 ISBN
        String isbn = "";
        Elements linhas = doc.select("#detailBullets_feature_div li");
        for (Element linha : linhas) {
            if (linha.text().toLowerCase().contains("isbn")) {
                isbn = linha.text().replaceAll("[^0-9X]", "");
                break;
            }
        }

        System.out.println("🔎 Extraído: título=" + titulo + ", preço=" + preco + ", ano=" + anoPublicacao + ", isbn=" + isbn);

        return new LivroDTO(null, titulo, isbn, anoPublicacao, preco, null, null);
    }
}

