package org.download.novels.service.driver.neoxscans;

import org.download.novels.extractor.JsoupParse;
import org.download.novels.repository.ChapterRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class NeoxScansHttp extends JsoupParse {
    protected NeoxScansHttp(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String getTitle(Document document) {
        document.select("input").remove();
        document.select("center").remove();
        document.select("ins").remove();
        document.select("div.adsbygoogle").remove();
        Element elementH2 = document.selectFirst("h2");
        if (elementH2 == null || elementH2.text().toLowerCase().contains("tradutor")) {
            return Objects.requireNonNull(document.selectFirst("h1[id=chapter-heading]")).html();
        }
        return Objects.requireNonNull(elementH2).html();
    }

    @Override
    protected String getContent(Element body) {
        body.select("div[id=text-chapter-toolbar]").remove();
        var element = body.select("div.reading-content");
        element.select("h1").remove();
        element.select("h2").remove();
        element.select("h3").remove();
        return element.html();
    }

    @Override
    protected String getNextPage(Document document) {
        return document.selectFirst("div.nav-next > a").attr("href");
    }


}
