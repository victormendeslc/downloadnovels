package org.download.novels.extractor;

import lombok.extern.slf4j.Slf4j;
import org.download.novels.repository.ChapterRepository;
import org.download.novels.repository.model.Chapter;
import org.download.novels.repository.model.Novel;

import java.util.Optional;

@Slf4j
public abstract class AbstractWriter implements IWriter {

    private final ChapterRepository chapterRepository;

    protected Integer numberChapter = 0;

    protected AbstractWriter(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    @Override
    public void execute(Novel novel, String page, boolean prologue) {
        if (prologue) {
            numberChapter = -1;
        }
        Thread thread = new Thread(() -> execute(novel, page));
        thread.setName(novel.getNovelName().toLowerCase().trim().replace(" ",""));
        thread.start();
    }

    protected void verifyChapter(Novel novel) {
        if (!novel.getChapters().isEmpty() && numberChapter <= 0) {
            Optional<Chapter> lastElement = novel.lastChapter();
            lastElement.ifPresent(chapter -> numberChapter = chapter.getPage());
        }
    }

    protected Chapter getChapter(Novel novel, String title, String content, String index, String nextPage) {
        Chapter chapter = new Chapter();
        chapter.setPage(getNumberChapter(nextPage));
        chapter.setNovelIndex(index);
        chapter.setNovel(novel);
        chapter.setTitle(title);
        chapter.setContent(content);
        chapter.setNextChapter(nextPage);
        return chapter;
    }

    protected Integer getNumberChapter(String nextPage) {
        return this.numberChapter;
    }

    protected String createTitle(String titulo) {
        numberChapter++;
        return "<h1><a name=\"chapter" + numberChapter + "\"</a>" + titulo + "</h1><br>";
    }

    protected void save(Chapter chapter) {
        chapterRepository.save(chapter);
    }
}
