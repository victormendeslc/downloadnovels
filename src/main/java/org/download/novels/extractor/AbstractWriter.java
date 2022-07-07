package org.download.novels.extractor;

import org.download.novels.repository.ChapterRepository;
import org.download.novels.repository.model.Chapter;
import org.download.novels.repository.model.Novel;

import java.util.Comparator;
import java.util.Optional;

public abstract class AbstractWriter implements IWriter {

    private final ChapterRepository chapterRepository;

    protected Integer numberChapter = 0;

    public AbstractWriter(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    protected void verifyChapter(Novel novel) {
        if (!novel.getChapters().isEmpty() && numberChapter == 0) {
            Optional<Chapter> lastElement = novel.getChapters().stream()
                    .sorted(Comparator.comparingInt(Chapter::getChapter))
                    .reduce((first, second) -> second);
            lastElement.ifPresent(chapter -> numberChapter = chapter.getChapter());
        }
    }

    protected Chapter getChapter(Novel novel, String title, String content) {
        Chapter chapter = new Chapter();
        chapter.setChapter(this.numberChapter);
        chapter.setNovel(novel);
        chapter.setTitle(title);
        chapter.setContent(content);
        return chapter;
    }

    protected String createTitle(String titulo) {
        numberChapter++;
        return "<h1><a name=\"chapter" + numberChapter + "\"</a>" + titulo + "</h1><br>";
    }

    protected void save(Chapter chapter) {
        chapterRepository.save(chapter);
    }
}
