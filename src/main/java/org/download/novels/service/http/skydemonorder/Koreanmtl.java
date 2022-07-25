package org.download.novels.service.http.skydemonorder;


import org.download.novels.extractor.JsoupParse;
import org.download.novels.repository.ChapterRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Objects;

@Service
public class Koreanmtl extends JsoupParse {
    public Koreanmtl(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String getTitle(Document document) {
        return Objects.requireNonNull(document.selectFirst("h3.post-title")).text();
    }

    @Override
    protected String getContent(Element body) {
        return body.select("div.post-body").html();
    }

    @Override
    protected String getNextPage(Document document) {
        URI uri = URI.create(document.baseUri());

        Element element = document.selectFirst("a.Next");
        if(element != null && !element.attr("href").isEmpty()) {
            return element.attr("href");
        }
        return null;
    }
}
