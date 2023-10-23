package org.download.novels.service.http.vulcan;

import org.download.novels.extractor.JsoupParse;
import org.download.novels.repository.ChapterRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class Vulcan extends JsoupParse {
    protected Vulcan(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String getTitle(Document document) {
        return Objects.requireNonNull(document.selectFirst("h1.elementor-heading-title")).html();
    }

    @Override
    protected String getContent(Element body) {
        return Objects.requireNonNull(body.select("div.entry-content")).html();
    }

    @Override
    protected String getNextPage(Document document) {
        return document.select("div.elementor-post-navigation__next > a[rel=next] ").attr("href");
    }
}
