package org.download.novels.service.http.woopread;

import org.download.novels.extractor.JsoupParse;
import org.download.novels.repository.ChapterRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
public class Woopread extends JsoupParse {

    public Woopread(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String getTitle(Document document) {
        return Objects.requireNonNull(document.selectFirst("li.active")).html();
    }

    @Override
    protected String getContent(Element body) {
        Element content = Objects.requireNonNull(body.selectFirst("div.reading-content"));

        Arrays.asList(
                content.select("script"),
                content.select("style"),
                content.select("ins"),
                content.select("img"),
                content.select("input")
        ).forEach(Elements::remove);
        return content.html();
    }

    @Override
    protected String getNextPage(Document document) {
        Element element = document.selectFirst("div.nav-next > a");
        if (element != null && !element.attr("href").isEmpty()) {
            return element.attr("href");
        }
        return null;
    }
}
