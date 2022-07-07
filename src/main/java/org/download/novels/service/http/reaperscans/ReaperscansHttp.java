package org.download.novels.service.http.reaperscans;

import org.download.novels.extractor.JsoupParse;
import org.download.novels.repository.ChapterRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReaperscansHttp extends JsoupParse {

    public ReaperscansHttp(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String getTitle(Document document) {
        return Objects.requireNonNull(document.selectFirst("li.active")).html();
    }

    @Override
    protected String getContent(Element body) {
        return body.select("div.text-left").html();
    }

    @Override
    protected String getNextPage(Document document) {
        return document.select("div.nav-next > a").attr("href");
    }

}
