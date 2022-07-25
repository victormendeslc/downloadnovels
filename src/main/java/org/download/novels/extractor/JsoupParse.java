package org.download.novels.extractor;

import lombok.extern.slf4j.Slf4j;
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
        Thread thread = new Thread(() -> {
            retry(novel, url, true);
        });
        thread.setName(novel.getNovelName().trim().toLowerCase());
        thread.start();
    }

    private void retry(Novel novel, String url, boolean retry) {
        try {
            Document doc = Jsoup.connect(url).get();
            log.info("Chapter {} and Connected {} ", numberChapter, url);
            Element body = doc.body();
            String index = getTitle(doc);
            String title = createTitle(getTitle(doc));
            String content = getContent(body);
            String nextPage = getNextPage(doc);

            Chapter chapter = getChapter(novel, title, content, index);
            chapter.setNextChapter(nextPage);
            save(chapter);

            if (verifyNextPage(nextPage)) {
                execute(novel, nextPage);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (retry) {
                retry(novel, url, false);
            }
        }
        log.info("Finished");
    }

    private boolean verifyNextPage(String nextPage) {
        return !nextPage.isEmpty() && !nextPage.contains("javascript") && !nextPage.contains("#");
    }

    protected abstract String getTitle(Document document);

    protected abstract String getContent(Element body);

    protected abstract String getNextPage(Document document);

}
