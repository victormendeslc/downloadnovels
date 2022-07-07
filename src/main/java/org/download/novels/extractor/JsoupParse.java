package org.download.novels.extractor;

import org.download.novels.repository.ChapterRepository;
import org.download.novels.repository.model.Chapter;
import org.download.novels.repository.model.Novel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public abstract class JsoupParse extends AbstractWriter {

    public JsoupParse(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    public void execute(Novel novel, String file, String url) {
        try {
            verifyChapter(novel);

            Document doc = Jsoup.connect(url).get();

            Element body = doc.body();
            String title = createTitle(getTitle(doc));
            String content = getContent(body);
            String nextPage = getNextPage(doc);

            Chapter chapter = getChapter(novel, title, content);
            chapter.setNextChapter(nextPage);
            save(chapter);
            if (!nextPage.isEmpty()) {
                execute(novel, file, nextPage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract String getTitle(Document document);

    protected abstract String getContent(Element body);

    protected abstract String getNextPage(Document document);

}
