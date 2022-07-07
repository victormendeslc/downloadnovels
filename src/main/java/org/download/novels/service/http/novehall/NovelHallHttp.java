package org.download.novels.service.http.novehall;


import org.download.novels.extractor.JsoupParse;
import org.download.novels.extractor.Writer;
import org.download.novels.repository.ChapterRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class NovelHallHttp extends JsoupParse {
    public NovelHallHttp(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String getTitle(Document document) {
        return document.select("div.single-header").select("h1").text();
    }

    @Override
    protected String getContent(Element body) {
        return body.select("div.entry-content").html();
    }

    @Override
    protected String getNextPage(Document document) {
        URI uri = URI.create(document.baseUri());
        String href = document.select("nav.nav-single > a[rel=\"next\"]").attr("href");
        if(href.isEmpty()){
            return href;
        }
        return uri.getScheme().concat("://").concat(uri.getHost()).concat(href);
    }
}
