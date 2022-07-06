package org.download.novels.service.lightnovel;

import org.download.novels.leitor.Writer;
import org.download.novels.repository.ChapterRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;


@Service
public class LightNovel extends Writer {

    public LightNovel(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String extractTitle(ChromeDriver driver) {
        WebElement chapterPage = driver.findElement(By.cssSelector("section.page-in"));
        return chapterPage.findElement(By.cssSelector("span.chapter-title")).getText();
    }

    @Override
    protected String extractContent(ChromeDriver driver) {
        WebElement chapterPage = driver.findElement(By.cssSelector("section.page-in"));
        WebElement content = chapterPage.findElement(By.id("chapter-container"));
        return content.getAttribute("innerHTML");
    }

    @Override
    protected String nextPage(ChromeDriver driver) {
        WebElement chapterPage = driver.findElement(By.cssSelector("section.page-in"));

        boolean naoExiste = chapterPage.findElements(By.linkText("NEXT")).isEmpty();
        if (!naoExiste) {
            return chapterPage.findElement(By.linkText("NEXT")).getAttribute("href");
        }
        return null;
    }
}
