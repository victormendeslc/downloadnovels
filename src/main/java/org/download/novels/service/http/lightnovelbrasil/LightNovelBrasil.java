package org.download.novels.service.http.lightnovelbrasil;

import org.download.novels.extractor.JsoupParse;
import org.download.novels.repository.ChapterRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

@Service
public class LightNovelBrasil extends JsoupParse {

    protected LightNovelBrasil(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String getTitle(Document document) {
        var element = document.selectFirst("div.cat-series");
        if (element == null) {
            element = document.selectFirst("h1.entry-title");
        }
        return Objects.requireNonNull(element).html();
    }

    @Override
    protected String getContent(Element body) {
        return Objects.requireNonNull(body.selectFirst("div.epcontent")).html();
    }

    @Override
    protected String getNextPage(Document document) {
        return document.select("div.nvs > a[rel=next] ").attr("href");
    }

    @Override
    protected Integer getNumberChapter(String nextPage) {
        if(StringUtils.hasText(nextPage)) {
            var pageNumberString = "-" + (numberChapter + 1);
            if (nextPage.contains(pageNumberString)) {
                return super.getNumberChapter(nextPage);

            }
            var page = Arrays.stream(nextPage.split("-")).reduce((first, second) -> second).map(str -> str.replace("/", "")).orElseThrow();
            return Integer.valueOf(page);
        }
        return null;
    }
}
