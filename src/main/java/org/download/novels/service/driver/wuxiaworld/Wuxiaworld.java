package org.download.novels.service.driver.wuxiaworld;

import org.download.novels.extractor.Writer;
import org.download.novels.repository.ChapterRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

@Service
public class Wuxiaworld extends Writer {

    public Wuxiaworld(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String extractTitle(WebDriver driver) {
        WebElement chapterPage = getChapterPage(driver);
        return chapterPage.findElement(By.cssSelector("h4.MuiTypography-root")).getAttribute("innerHTML");
    }

    private WebElement getChapterPage(WebDriver driver) {
        return driver.findElement(By.id("chapter-page"));
    }

    @Override
    protected String extractContent(WebDriver driver) {
        WebElement chapterPage = getChapterPage(driver);
        WebElement content = chapterPage.findElement(By.cssSelector("div.chapter-content"));
        return content.getAttribute("innerHTML");
    }

    @Override
    protected String nextPage(WebDriver driver) {
        WebElement chapterPage = getChapterPage(driver);
        boolean naoExiste = chapterPage.findElements(By.linkText("NEXT CHAPTER")).isEmpty();
        if (!naoExiste) {
            return chapterPage.findElement(By.linkText("NEXT CHAPTER")).getAttribute("href");
        }
        return null;
    }
}
