package org.download.novels.extractor;

import lombok.extern.slf4j.Slf4j;
import org.download.novels.repository.ChapterRepository;
import org.download.novels.repository.model.Chapter;
import org.download.novels.repository.model.Novel;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class AbstractWriter implements IWriter {

    private final ChapterRepository chapterRepository;

    protected AtomicInteger numberChapter;

    protected AbstractWriter(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
        this.numberChapter = new AtomicInteger(1);
    }

    @Override
    public void executeFirst(Novel novel, String page, boolean prologue) {
        if (prologue) {
            setNumberChapter(new AtomicInteger(0));
        }
        Thread thread = new Thread(() -> execute(novel, page));
        thread.setName(novel.getNovelName().toLowerCase().trim().replace(" ", ""));
        thread.start();
        log.info("Execute thread {}", thread.getName());
    }

    protected void verifyChapter(Novel novel) {
        if (!novel.getChapters().isEmpty() && getNumberChapter().get() <= 1) {
            Optional<Chapter> lastElement = novel.lastChapter();
            lastElement.ifPresent(chapter -> setNumberChapter(new AtomicInteger(chapter.getPage())));
        }
    }

    protected Chapter getChapter(Novel novel, String title, String content, String index, String nextPage) {
        Chapter chapter = new Chapter();
        chapter.setPage(getNumberChapter(nextPage).get());
        chapter.setNovelIndex(index);
        chapter.setNovel(novel);
        chapter.setTitle(title);
        chapter.setContent(content);
        chapter.setNextChapter(nextPage);
        return chapter;
    }

    private AtomicInteger getNumberChapter() {
        return this.numberChapter;
    }

    protected void setNumberChapter(AtomicInteger atomicInteger) {
        this.numberChapter = atomicInteger;
    }

    protected AtomicInteger getNumberChapter(String nextPage) {
        return this.numberChapter;
    }

    protected String createTitle(String titulo) {
        getNumberChapter().incrementAndGet();
        return "<h1><a name=\"chapter" + getNumberChapter().get() + "\"</a>" + titulo + "</h1><br>";
    }

    protected void save(Chapter chapter) {
        chapterRepository.save(chapter);
    }
}
