package org.download.novels.service.http.centralnovel;

import org.download.novels.extractor.JsoupParse;
import org.download.novels.repository.ChapterRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CentralNovel extends JsoupParse {

    public CentralNovel(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String getTitle(Document document) {
        return Objects.requireNonNull(document.selectFirst("h1.entry-title")).html();
    }

    @Override
    protected String getContent(Element body) {
        return Objects.requireNonNull(body.selectFirst("div.epcontent")).html();
    }

    @Override
    protected String getNextPage(Document document) {
        return document.select("div.bottomnav > a[rel=next] ").attr("href");
    }
}