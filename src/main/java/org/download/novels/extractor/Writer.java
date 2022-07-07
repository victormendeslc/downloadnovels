package org.download.novels.extractor;

import org.download.novels.repository.ChapterRepository;
import org.download.novels.repository.model.Chapter;
import org.download.novels.repository.model.Novel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public abstract class Writer extends AbstractWriter {

    public Writer(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    public void execute(Novel novel, String file, String url) {
        verifyChapter(novel);

        Thread thread = new Thread(() -> {
            doExecute(novel, url);
        });
        thread.setName(novel.getNovelName().trim().toLowerCase().concat(String.valueOf(numberChapter)));
        thread.start();
    }

    private void doExecute(Novel novel, String url) {
        WebDriver driver = createDriver(url);
        Chapter chapter = createChapter(novel, driver);
        saveAndVerifyNext(novel, driver, chapter);
    }

    private Chapter createChapter(Novel novel, WebDriver driver) {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(15));

        String title = driverWait.until(d -> createTitle(extractTitle(d)));
        String content = driverWait.until(this::extractContent);

        return getChapter(novel, title, content);
    }

    private void saveAndVerifyNext(Novel novel, WebDriver driver, Chapter chapter) {
        String hrefNext = nextPage(driver);
        if (Objects.nonNull(hrefNext)) {
            chapter.setNextChapter(hrefNext);
            save(chapter);
            closeDriver(driver);
            doExecute(novel, hrefNext);
        } else {
            save(chapter);
            closeDriver(driver);
        }
    }

    protected abstract String extractTitle(WebDriver driver);

    protected abstract String extractContent(WebDriver driver);

    protected abstract String nextPage(WebDriver driver);

    private void closeDriver(WebDriver driver) {
        driver.close();
        driver.quit();
    }

    private ChromeDriver createDriver(String url) {
        ChromeDriver driver = new ChromeDriver();

        driver.get(url);
        return driver;
    }


}
