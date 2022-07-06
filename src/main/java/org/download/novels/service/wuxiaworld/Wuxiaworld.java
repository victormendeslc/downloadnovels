package org.download.novels.service.wuxiaworld;

import org.download.novels.leitor.Writer;
import org.download.novels.repository.ChapterErrorRepository;
import org.download.novels.repository.ChapterRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Service
public class Wuxiaworld extends Writer {

    public Wuxiaworld(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String extractTitle(ChromeDriver driver) {
        WebElement chapterPage = driver.findElement(By.id("chapter-page"));
        return chapterPage.findElement(By.cssSelector("h4.MuiTypography-root")).getAttribute("innerHTML");
    }

    @Override
    protected String extractContent(ChromeDriver driver) {
        WebElement chapterPage = driver.findElement(By.id("chapter-page"));
        WebElement content = chapterPage.findElement(By.cssSelector("div.chapter-content"));
        return content.getAttribute("innerHTML");
    }

    @Override
    protected String nextPage(ChromeDriver driver) {
        WebElement chapterPage = driver.findElement(By.id("chapter-page"));
        boolean naoExiste = chapterPage.findElements(By.linkText("NEXT CHAPTER")).isEmpty();
        if (!naoExiste) {
            return chapterPage.findElement(By.linkText("NEXT CHAPTER")).getAttribute("href");
        }
        return null;
    }
}
