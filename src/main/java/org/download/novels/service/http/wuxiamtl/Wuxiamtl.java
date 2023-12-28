package org.download.novels.service.http.wuxiamtl;

import org.download.novels.extractor.JsoupParse;
import org.download.novels.repository.ChapterRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Objects;

@Service
public class Wuxiamtl extends JsoupParse {
    protected Wuxiamtl(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String getTitle(Document document) {
        return Objects.requireNonNull(document.selectFirst("div.titles")).html();
    }

    @Override
    protected String getContent(Element body) {
        return  Objects.requireNonNull(body.selectFirst("div.chapter-content")).html();
    }


    @Override
    protected String getNextPage(Document document) {
        URI uri = URI.create(document.baseUri());
        var element =  Objects.requireNonNull(document.selectFirst("div.action-select > a > span:contains(Next)")).parent();
        String href = element.attr("href");
        if (href.isEmpty() || href.contains("javascript")) {
            return href;
        }
        return uri.getScheme().concat("://").concat(uri.getHost()).concat(href);
    }
}
