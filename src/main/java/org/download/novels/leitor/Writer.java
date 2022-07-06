package org.download.novels.leitor;

import org.download.novels.model.Chapter;
import org.download.novels.model.Novel;
import org.download.novels.repository.ChapterRepository;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public abstract class Writer implements Extractor {

    private final ChapterRepository chapterRepository;

    private Integer numberChapter = 0;

    public Writer(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    @Override
    public void execute(Novel novel, String file, String url) {
        execute(novel, file, url, false);
    }

    public void execute(Novel novel, String file, String url, boolean navigate) {
        if (!novel.getChapters().isEmpty() && numberChapter == 0) {
            Optional<Chapter> lastElement = novel.getChapters().stream()
                    .sorted(Comparator.comparingInt(Chapter::getChapter))
                    .reduce((first, second) -> second);
            lastElement.ifPresent(chapter -> numberChapter = chapter.getChapter());
        }
        Thread thread;
        if (navigate) {
            thread = new Thread(() -> {
                ChromeDriver driver = new ChromeDriver();
                doExecute(novel, url, driver);
            });
        } else {
            thread = new Thread(() -> {
                doExecute(novel, url);
            });
        }
        thread.setName(file);
        thread.start();

    }

    private void doExecute(Novel novel, String url, ChromeDriver driver) {
        driver.navigate().to(url);
        try {
            driver.wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Chapter chapter = createChapter(novel, driver);
        String hrefNext = nextPage(driver);
        if (Objects.nonNull(hrefNext)) {
            chapter.setNextChapter(hrefNext);
            chapterRepository.save(chapter);
            doExecute(novel, hrefNext, driver);
        } else {
            chapterRepository.save(chapter);
        }
        closeDriver(driver);
    }

    private void doExecute(Novel novel, String url) {
        try {
            ChromeDriver driver = createDriver(url);
            String hrefNext = nextPage(driver);
            Chapter chapter = createChapter(novel, driver);

            saveChapter(novel, driver, hrefNext, chapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected abstract String extractTitle(ChromeDriver driver);

    protected abstract String extractContent(ChromeDriver driver);

    protected abstract String nextPage(ChromeDriver driver);

    private String createTitle(String titulo) {
        numberChapter++;
        return "<h1><a name=\"chapter" + numberChapter + "\"</a>" + titulo + "</h1><br>";
    }

    private void closeDriver(ChromeDriver driver) {
        driver.close();
        driver.quit();
    }

    private ChromeDriver createDriver(String url) {
        ChromeDriver driver = new ChromeDriver();

        driver.get(url);
        return driver;
    }

    private Chapter createChapter(Novel novel, ChromeDriver driver) {
        String title = createTitle(extractTitle(driver));
        String content = extractContent(driver);
        Chapter chapter = new Chapter();
        chapter.setChapter(numberChapter);
        chapter.setNovel(novel);
        chapter.setTitle(title);
        chapter.setContent(content);
        return chapter;
    }

    private void saveChapter(Novel novel, ChromeDriver driver, String hrefNext, Chapter chapter) {
        if (Objects.nonNull(hrefNext)) {
            chapter.setNextChapter(hrefNext);
            chapterRepository.save(chapter);
            closeDriver(driver);
            doExecute(novel, hrefNext);
        } else {
            chapterRepository.save(chapter);
            closeDriver(driver);
        }
    }


}
