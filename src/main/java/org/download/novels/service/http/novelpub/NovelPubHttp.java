package org.download.novels.service.http.novelpub;


import org.download.novels.extractor.JsoupParse;
import org.download.novels.repository.ChapterRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Objects;

@Service
public class NovelPubHttp extends JsoupParse {
    public NovelPubHttp(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String getTitle(Document document) {
        return Objects.requireNonNull(document.selectFirst("span.chapter-title")).text();
    }

    @Override
    protected String getContent(Element body) {
        return body.select("div.chapter-content").html();
    }

    @Override
    protected String getNextPage(Document document) {
        URI uri = URI.create(document.baseUri());
        String href = document.select("a.button.nextchap").attr("href");
        if(href.isEmpty() || href.contains("javascript")){
            return href;
        }
        return uri.getScheme().concat("://").concat(uri.getHost()).concat(href);
    }
}