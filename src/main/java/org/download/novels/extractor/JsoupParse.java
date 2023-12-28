package org.download.novels.extractor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.download.novels.repository.ChapterRepository;
import org.download.novels.repository.model.Chapter;
import org.download.novels.repository.model.Novel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

@Slf4j
public abstract class JsoupParse extends AbstractWriter {

    protected JsoupParse(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    public void execute(Novel novel, String url) {
        verifyChapter(novel);
        retry(novel, url, true);
    }

    private void retry(Novel novel, String url, boolean retry) {
        try {
            Document doc = Jsoup.connect(url).get();
            doc.select("img").remove();
            doc.select("script").remove();
            doc.select("criador").remove();

            log.info("Chapter {} and Connected {} ", numberChapter, url);
            Element body = doc.body();
            String index = getTitle(doc);
            String title = createTitle(getTitle(doc));
            String content = getContent(body);
            String nextPage = getNextPage(doc);

            Chapter chapter = getChapter(novel, title, content, index, nextPage);
            save(chapter);

            if (verifyNextPage(nextPage) && ObjectUtils.isNotEmpty(content)) {
                execute(novel, nextPage);
            }
            log.info("Finish {}", novel.getNovelName());
        } catch (IOException e) {
            e.printStackTrace();
            if (retry) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                retry(novel, url, false);
            }
        }
    }

    private boolean verifyNextPage(String nextPage) {
        return nextPage != null && !nextPage.isEmpty() && !nextPage.contains("javascript") && !nextPage.contains("#");
    }

    protected abstract String getTitle(Document document);

    protected abstract String getContent(Element body);

    protected abstract String getNextPage(Document document);

}
